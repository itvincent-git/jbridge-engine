package net.jbridge.compiler.processor


import net.jbridge.annotation.JBridge2Js
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridge2JsGetMethod
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethod
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethodParameter
import net.jbridge.util.JavaxUtil
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.TypeMirror

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
                    var jBridgeContextGenericType: TypeMirror? = null
                    var jBridgeToJsGetMethod: JBridge2JsGetMethod? = null
                    var isJBridgeToJsInterface = false

                    val type = it.asType()
                    if (!type.kind.isPrimitive) {
                        val typeElement = JavaxUtil.toTypeElement(type)

                        if (typeElement.qualifiedName.toString() == "net.jbridge.runtime.JBridgeContext") {
                            jBridgeContextGenericType = JavaxUtil.getTypeParameters(type).firstOrNull()
                            //compileContext.log.debug("Js2JBridgeInterfaceMethodProcessor hasJBridgeContext $typeElement <$jBridgeContextGenericType>")
                            isJBridgeContext = true
                        } else if (typeElement.getAnnotation(JBridge2Js::class.java) != null){
                            isJBridgeToJsInterface = true
                            jBridge2JsMethods.forEach {
                                if (it.returnElement == typeElement)
                                    jBridgeToJsGetMethod = it
                            }
                        }


                    }
                    Js2JBridgeInterfaceMethodParameter(it, isJBridgeContext, jBridgeContextGenericType, isJBridgeToJsInterface, jBridgeToJsGetMethod).apply {
                        compileContext.log.debug("Js2JBridgeInterfaceMethodProcessor %s", it)
                    }
                }

        return Js2JBridgeInterfaceMethod(executableElement, parameters).apply {
            compileContext.log.debug("Js2JBridgeInterfaceMethod process %s:%s", executableElement.toString(), this)
        }
    }
}
