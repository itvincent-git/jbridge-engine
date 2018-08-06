package net.jbridge.compiler.writer

import com.squareup.javapoet.*
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridge2JsGetMethod
import net.jbridge.compiler.data.JBridgeData
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethod
import net.jbridge.util.TmpVar
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement

/**
 * 生成XXXJBridge_Impl
 * Created by zhongyongsheng on 2018/4/14.
 */
class JBridgeClassWriter(val jbridgeData: JBridgeData,
                         val compilerContext: CompilerContext) : JBridgeBaseWriter(jbridgeData.implTypeName) {

    override fun createTypeSpecBuilder(): TypeSpec.Builder {
        val builder = TypeSpec.classBuilder(jbridgeData.implTypeName)
        builder.addModifiers(Modifier.PUBLIC)
                .superclass(jbridgeData.typeName)
        addJavascriptInterfaceMethod(builder)
        addJBridgeToJsMethod(builder)
        createOnJsToBridgeMethod(builder, false)
        createOnJsToBridgeMethod(builder, true)
        return builder
    }

    /**
     * 生成@JavascriptInterface方法
     */
    private fun addJavascriptInterfaceMethod(builder: TypeSpec.Builder) {
        jbridgeData.js2BridgeFields.forEach { getMethod ->
            getMethod.js2JBridgeData.methods.forEach { interfaceMethod ->
                val executableElement = interfaceMethod.executableElement
                var fieldElement = getMethod.element

                MethodSpec.methodBuilder(executableElement.simpleName.toString())
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(ClassName.get("android.webkit", "JavascriptInterface"))
                        .apply {
                            addJavascriptInterfaceInner(this, fieldElement, executableElement, interfaceMethod)
                            interfaceMethod.parameters
                                .filter { !it.isJBridgeContext && !it.isJBridgeToJsInterface }//不生成这些方法定义
                                .forEach {
                                    this.addParameter(ParameterSpec.get(it.variableElement))
                                }
                            builder.addMethod(this.build())
                        }

            }
        }
    }

    /**
     * 生成@JavascriptInterface方法内的代码块
     */
    private fun addJavascriptInterfaceInner(methodSpec: MethodSpec.Builder, fieldElement:VariableElement,
                                    executableElement: ExecutableElement, interfaceMethod: Js2JBridgeInterfaceMethod) {
        methodSpec.addStatement("\$L.\$L(\$L)",
                fieldElement.toString(),//fieldName
                executableElement.simpleName.toString(),//methodName
                interfaceMethod.parameters.joinToString {
                    if (it.isJBridgeContext) {//处理JBridgeContext类型
                        return@joinToString "bridgeContext"
                    }
                    if (it.isJBridgeToJsInterface) {//处理JBridge2Js接口
                        return@joinToString it.jBridgeToJsGetMethod?.element.toString()
                    }
                    return@joinToString it.variableElement.simpleName
                })//param list
    }


    /**
     * 生成IJsInterface方法
     */
    private fun addJBridgeToJsMethod(builder: TypeSpec.Builder) {
        val tmpVar = TmpVar()
        jbridgeData.jBridge2JsMethods.forEach {
            val name = it.jBridge2JsData.typeName.simpleName().decapitalize()

            val fieldName = tmpVar.getTmpVar("_$name")
            val field = FieldSpec.builder(it.jBridge2JsData.typeName,
                    fieldName,
                    Modifier.PRIVATE,
                    Modifier.VOLATILE)
                    .build()
            builder.addField(field)
            builder.addMethod(createInterfaceGetter(field, it))
        }
    }

    /**
     * 生成getJsInterface()方法
     */
    private fun createInterfaceGetter(field: FieldSpec, method: JBridge2JsGetMethod): MethodSpec {
        val methodBuilder = MethodSpec.overriding(net.jbridge.util.JavaxUtil.asExecutable(method.element))
        methodBuilder.beginControlFlow("if (\$N != null)", field).addStatement("return \$N", field)
        methodBuilder.nextControlFlow("else")
                .beginControlFlow("synchronized(this)")
                .beginControlFlow("if (\$N == null)", field)
                .addStatement("\$N = new \$T(\$L)", field, method.jBridge2JsData.implTypeName, jbridgeData.jBridgeCallbackField.toString())
                .endControlFlow()
                .addStatement("return \$N", field)
                .endControlFlow()
                .endControlFlow()
        methodBuilder.addModifiers(Modifier.PUBLIC)
        return methodBuilder.build()
    }

    //生成onJsToBridge和onJsToBridgeSync
    private fun createOnJsToBridgeMethod(builder: TypeSpec.Builder, isSyncReturn: Boolean) {
        val theCreateMethod = if (isSyncReturn) jbridgeData.onJsToBridgeSync else jbridgeData.onJsToBridge
        theCreateMethod?.let { onJsToBridgeElement ->
            val methodBuilder = MethodSpec.overriding(onJsToBridgeElement)
            val msgName = onJsToBridgeElement.parameters[0].simpleName.toString()
            val paramMapName = onJsToBridgeElement.parameters[1].simpleName.toString()

            //生成switch
            val codeBlock = CodeBlock.builder()
            codeBlock.beginControlFlow("switch ($msgName)")
            jbridgeData.js2BridgeFields.forEach { getMethod ->
                getMethod.js2JBridgeData.methods
                    .filter {
                        if (isSyncReturn)
                            it.executableElement.returnType.toString() != "void"//只处理有返回值
                        else
                            it.executableElement.returnType.toString() == "void"//只处理没返回值
                    }
                    .forEach { interfaceMethod ->
                        val executableElement = interfaceMethod.executableElement
                        var fieldElement = getMethod.element

                        codeBlock.add("case \$S:\n", kotlin.run {
                            return@run "${executableElement.simpleName}(" +
                            interfaceMethod.parameters.filter { !it.isJBridgeContext && !it.isJBridgeToJsInterface }//不生成这些方法定义
                                .joinToString {
                                    it.variableElement.toString()
                                } + ")"
                        })
                        codeBlock.add("\t\$L\$L.\$L(\$L);\n",
                                kotlin.run {
                                    if (interfaceMethod.executableElement.returnType.toString() != "void")
                                        return@run "return "//有返回值要加上return
                                    else
                                        return@run ""
                                },
                                fieldElement.toString(),//fieldName
                                executableElement.simpleName.toString(),//methodName
                                interfaceMethod.parameters.joinToString {
                                    if (it.isJBridgeContext) {//处理JBridgeContext类型
                                        return@joinToString "bridgeContext"
                                    }
                                    if (it.isJBridgeToJsInterface) {//处理JBridge2Js接口
                                        return@joinToString it.jBridgeToJsGetMethod?.element.toString()
                                    }
                                    return@joinToString "(${it.variableElement.asType()})${paramMapName}.get(\"${it.variableElement.simpleName}\")"
                                })//param list
                        if (!isSyncReturn) codeBlock.add("\tbreak;\n")//没返回值要加上break
                }
            }
            codeBlock.endControlFlow()
            if (isSyncReturn) codeBlock.add("return null;\n")//有返回值要加上一个默认的return

            methodBuilder.addCode(codeBlock.build())
            builder.addMethod(methodBuilder.build())
        }

    }


}
