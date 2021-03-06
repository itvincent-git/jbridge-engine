package net.jbridge.compiler.processor


import net.jbridge.annotation.JBridge2Js
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridge2JsGetMethod
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethod
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethodParameter
import net.jbridge.util.JavaxUtil
import javax.lang.model.element.ExecutableElement

/**
 * 处理@Js2JBridge标注的类的信息
 * Created by zhongyongsheng on 2018/4/14.
 */
class Js2JBridgeInterfaceMethodProcessor internal constructor(val compileContext: CompilerContext,
                                                              val executableElement: ExecutableElement,
                                                              val jBridge2JsMethods: List<JBridge2JsGetMethod>) {
    internal fun process(): Js2JBridgeInterfaceMethod {
        val parameters = executableElement.getParameters()
                .map {
                    var isJBridgeContext = false
                    var jBridgeToJsGetMethod: JBridge2JsGetMethod? = null
                    var isJBridgeToJsInterface = false

                    val type = it.asType()
                    if (!JavaxUtil.isPrimitiveType(type) && !JavaxUtil.isArrayType(type)) {
                        val typeElement = JavaxUtil.toTypeElement(type)

                        if (typeElement.qualifiedName.toString() == "net.jbridge.runtime.JBridgeContext") {
                            isJBridgeContext = true
                        } else if (typeElement.getAnnotation(JBridge2Js::class.java) != null){
                            isJBridgeToJsInterface = true
                            jBridge2JsMethods.forEach {
                                if (it.returnElement == typeElement)
                                    jBridgeToJsGetMethod = it
                            }
                        }
                    }
                    Js2JBridgeInterfaceMethodParameter(it, isJBridgeContext, isJBridgeToJsInterface, jBridgeToJsGetMethod)
                }

        return Js2JBridgeInterfaceMethod(executableElement, parameters)
    }
}
