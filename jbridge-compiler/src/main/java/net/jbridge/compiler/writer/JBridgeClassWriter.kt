package net.jbridge.compiler.writer

import com.squareup.javapoet.*
import net.jbridge.compiler.data.JBridgeData
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
                        .addStatement("\$L().\$L(\$L)",
                                    getMethodElement.simpleName.toString(),//getMethodName
                                    executableElement.simpleName.toString(),//methodName
                                    executableElement.parameters.map { it.simpleName }.joinToString())//param list
                        .apply {
                            executableElement.parameters.forEach {
                                this.addParameter(ParameterSpec.get(it))
                            }
                            builder.addMethod(this.build())
                        }

            }
        }
    }

}
