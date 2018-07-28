package net.jbridge.compiler.writer

import com.squareup.javapoet.*
import net.jbridge.compiler.data.JBridgeData
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethod
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


//    private fun addJBridgeToJsMethod(builder: TypeSpec.Builder) {
//        val tmpVar = TmpVar()
//        portTransformerData.portInterfaceMethodList.forEach { portInterfaceMethod ->
//            val name = StringUtil.decapitalize(
//                    portInterfaceMethod.portInterfaceData.typeName.simpleName())
//            val fieldName = tmpVar.getTmpVar("_$name")
//            val field = FieldSpec.builder(portInterfaceMethod.portInterfaceData.typeName,
//                    fieldName,
//                    Modifier.PRIVATE,
//                    Modifier.VOLATILE)
//                    .build()
//            builder.addField(field)
//            builder.addMethod(createInterfaceGetter(field, portInterfaceMethod))
//        }
//    }
//
//    private fun createInterfaceGetter(field: FieldSpec, portInterfaceMethod: PortInterfaceMethod): MethodSpec {
//        val methodBuilder = MethodSpec.overriding(Util.asExecutable(portInterfaceMethod.element))
//        methodBuilder.beginControlFlow("if (\$N != null)", field).addStatement("return \$N", field)
//        methodBuilder.nextControlFlow("else")
//                .beginControlFlow("synchronized(this)")
//                .beginControlFlow("if (\$N == null)", field)
//                .addStatement("\$N = new \$T()", field, portInterfaceMethod.portInterfaceData.implTypeName)
//                .endControlFlow()
//                .addStatement("return \$N", field)
//                .endControlFlow()
//                .endControlFlow()
//        methodBuilder.addModifiers(Modifier.PUBLIC)
//        return methodBuilder.build()
//    }

}
