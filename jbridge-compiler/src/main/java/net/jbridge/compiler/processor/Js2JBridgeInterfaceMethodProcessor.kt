package net.jbridge.compiler.processor


import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethod
import net.jbridge.util.Util
import javax.lang.model.element.ExecutableElement

/**
 * 处理@JBridge标注的类的信息
 * Created by zhongyongsheng on 2018/4/14.
 */
class Js2JBridgeInterfaceMethodProcessor internal constructor(internal var compileContext: CompilerContext,
                                                              internal var executableElement: ExecutableElement) {


    internal fun process(): Js2JBridgeInterfaceMethod {
        var hasJBridgeContext = false

        val parameters = executableElement.getParameters()
                .filter {
                    val type = it.asType()
                    if (!type.kind.isPrimitive) {
                        if (Util.toTypeElement(type).qualifiedName.toString() == "net.jbridge.JBridgeContext") {
                            compileContext.log.debug("Js2JBridgeInterfaceMethodProcessor hasJBridgeContext")
                            hasJBridgeContext = true
                            return@filter false
                        }
                    }
                    true
                }.apply {
                    this.forEach {
                        compileContext.log.debug("Js2JBridgeInterfaceMethodProcessor %s", it.simpleName)
                    }
                }



        return Js2JBridgeInterfaceMethod(executableElement, parameters, hasJBridgeContext).apply {
            compileContext.log.debug("Js2JBridgeInterfaceMethod process %s:%s", executableElement.toString(), this)
        }
    }
}
