package net.jbridge.compiler.writer

import com.squareup.javapoet.*
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
class JBridgeClassWriter(internal var jbridgeData: JBridgeData) : JBridgeBaseWriter(jbridgeData.implTypeName) {

    override fun createTypeSpecBuilder(): TypeSpec.Builder {
        val builder = TypeSpec.classBuilder(jbridgeData.implTypeName)
        builder.addModifiers(Modifier.PUBLIC)
                .superclass(jbridgeData.typeName)
        addJavascriptInterfaceMethod(builder)
        addJBridgeToJsMethod(builder)
        return builder
    }

    /**
     * 生成@JavascriptInterface方法
     */
    private fun addJavascriptInterfaceMethod(builder: TypeSpec.Builder) {
        jbridgeData.js2BridgeMethods.forEach { getMethod ->
            getMethod.js2JBridgeData.methods.forEach { interfaceMethod ->
                val executableElement = interfaceMethod.executableElement
                var fieldElement = getMethod.element

                MethodSpec.methodBuilder(executableElement.simpleName.toString())
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(ClassName.get("android.webkit", "JavascriptInterface"))
                        .apply {
                            addJavascriptInterfaceInner(this, fieldElement, executableElement, interfaceMethod)
                            interfaceMethod.parameters.forEach {

                                this.addParameter(ParameterSpec.get(it))
                            }
                            builder.addMethod(this.build())
                        }

            }
        }
    }

    private fun addJavascriptInterfaceInner(methodSpec: MethodSpec.Builder, fieldElement:VariableElement,
                                    executableElement: ExecutableElement, interfaceMethod: Js2JBridgeInterfaceMethod) {
        if (interfaceMethod.hasJBridgeContext) {
            methodSpec.addStatement("\$L.\$L((\$T)bridgeContext, \$L)",
                    fieldElement.toString(),//getMethodName
                    executableElement.simpleName.toString(),//methodName
                    ParameterizedTypeName.get(ClassName.get("net.jbridge.runtime", "JBridgeContext"),
                            jbridgeData.typeName),
                    interfaceMethod.parameters.joinToString { it.simpleName })//param list
        } else {
            methodSpec.addStatement("\$L.\$L(\$L)",
                    fieldElement.toString(),//getMethodName
                    executableElement.simpleName.toString(),//methodName
                    interfaceMethod.parameters.joinToString { it.simpleName })//param list
        }
    }


    /**
     * 生成IJsInterface方法
     */
    private fun addJBridgeToJsMethod(builder: TypeSpec.Builder) {
        val tmpVar = TmpVar()
        jbridgeData.jBridge2JsMethods.forEach {
            val name = it.js2JBridgeData.typeName.simpleName().decapitalize()//StringUtil.decapitalize(portInterfaceMethod.portInterfaceData.typeName.simpleName())

            val fieldName = tmpVar.getTmpVar("_$name")
            val field = FieldSpec.builder(it.js2JBridgeData.typeName,
                    fieldName,
                    Modifier.PRIVATE,
                    Modifier.VOLATILE)
                    .build()
            builder.addField(field)
            builder.addMethod(createInterfaceGetter(field, it))
        }
    }

    private fun createInterfaceGetter(field: FieldSpec, method: JBridge2JsGetMethod): MethodSpec {
        val methodBuilder = MethodSpec.overriding(net.jbridge.util.Util.asExecutable(method.element))
        methodBuilder.beginControlFlow("if (\$N != null)", field).addStatement("return \$N", field)
        methodBuilder.nextControlFlow("else")
                .beginControlFlow("synchronized(this)")
                .beginControlFlow("if (\$N == null)", field)
                .addStatement("\$N = new \$T(\$L)", field, method.js2JBridgeData.implTypeName, jbridgeData.jBridgeCallbackField.toString())
                .endControlFlow()
                .addStatement("return \$N", field)
                .endControlFlow()
                .endControlFlow()
        methodBuilder.addModifiers(Modifier.PUBLIC)
        return methodBuilder.build()
    }

}
