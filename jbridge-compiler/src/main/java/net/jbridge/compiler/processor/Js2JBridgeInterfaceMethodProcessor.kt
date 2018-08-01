package net.jbridge.compiler.processor


import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethod
import net.jbridge.util.JavaxUtil
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.TypeMirror

/**
 * 处理@Js2JBridge标注的类的信息
 * Created by zhongyongsheng on 2018/4/14.
 */
class Js2JBridgeInterfaceMethodProcessor internal constructor(internal var compileContext: CompilerContext,
                                                              internal var executableElement: ExecutableElement) {


    internal fun process(): Js2JBridgeInterfaceMethod {
        var hasJBridgeContext = false
        var jBridgeContextGenericType: TypeMirror? = null

        val parameters = executableElement.getParameters()
                .filter {
                    val type = it.asType()
                    if (!type.kind.isPrimitive) {
                        //compileContext.log.debug("Js2JBridgeInterfaceMethodProcessor %s", Util.toTypeElement(type).qualifiedName.toString())
                        val typeElement = JavaxUtil.toTypeElement(type)
                        if (typeElement.qualifiedName.toString() == "net.jbridge.runtime.JBridgeContext") {
                            jBridgeContextGenericType = JavaxUtil.getTypeParameters(type).firstOrNull()
                            compileContext.log.debug("Js2JBridgeInterfaceMethodProcessor hasJBridgeContext $typeElement <$jBridgeContextGenericType>")
                            hasJBridgeContext = true

                            return@filter false
                        }
                    }
                    true
                }

        return Js2JBridgeInterfaceMethod(executableElement, parameters, hasJBridgeContext, jBridgeContextGenericType).apply {
            compileContext.log.debug("Js2JBridgeInterfaceMethod process %s:%s", executableElement.toString(), this)
        }
    }
}
