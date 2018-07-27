package net.jbridge.compiler.writer

import com.squareup.javapoet.*
import net.jbridge.compiler.data.JBridgeData
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethod
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier

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

    private fun addJavascriptInterfaceMethod(builder: TypeSpec.Builder) {
        jbridgeData.js2BridgeMethods.forEach { getMethod ->
            getMethod.js2JBridgeData.methods.forEach { interfaceMethod ->
                val executableElement = interfaceMethod.executableElement
                var getMethodElement = getMethod.element

                MethodSpec.methodBuilder(executableElement.simpleName.toString())
                        .addAnnotation(ClassName.get("android.webkit", "JavascriptInterface"))
                        .apply {
                            addJavascriptInterfaceInner(this, getMethodElement, executableElement, interfaceMethod)
                            interfaceMethod.parameters.forEach {
                                this.addParameter(ParameterSpec.get(it))
                            }
                            builder.addMethod(this.build())
                        }

            }
        }
    }

    fun addJavascriptInterfaceInner(methodSpec: MethodSpec.Builder, getMethodElement:ExecutableElement,
                                    executableElement: ExecutableElement, interfaceMethod: Js2JBridgeInterfaceMethod) {


        if (interfaceMethod.hasJBridgeContext) {
            methodSpec.addStatement("\$L().\$L((\$T)bridgeContext, \$L)",
                    getMethodElement.simpleName.toString(),//getMethodName
                    executableElement.simpleName.toString(),//methodName
                    ParameterizedTypeName.get(ClassName.get("net.jbridge", "JBridgeContext"),
                            jbridgeData.typeName),
                    interfaceMethod.parameters.joinToString { it.simpleName })//param list
        } else {
            methodSpec.addStatement("\$L().\$L(\$L)",
                    getMethodElement.simpleName.toString(),//getMethodName
                    executableElement.simpleName.toString(),//methodName
                    interfaceMethod.parameters.joinToString { it.simpleName })//param list
        }

    }

}
