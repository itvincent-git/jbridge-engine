package net.jbridge.compiler.processor


import net.jbridge.annotation.Js2JBridge
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridgeData
import net.jbridge.compiler.data.Js2JBridgeData
import net.jbridge.util.Util
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * Created by zhongyongsheng on 2018/4/14.
 */
class Js2JBridgeProcessor internal constructor(internal var compileContext: CompilerContext,
                                               internal var js2bridgeInterfaceElement: TypeElement) {


    internal fun process(): Js2JBridgeData {

        if (js2bridgeInterfaceElement.getAnnotation(Js2JBridge::class.java) == null) {
            compileContext.log.error(js2bridgeInterfaceElement, "${js2bridgeInterfaceElement.getSimpleName()} must has @Js2JBridge")
        }
        if (js2bridgeInterfaceElement.getKind() != ElementKind.INTERFACE) {
            compileContext.log.error(js2bridgeInterfaceElement, "${js2bridgeInterfaceElement.getSimpleName()} must be interface")
        }

        val declaredType = Util.asDeclared(js2bridgeInterfaceElement)

        return Js2JBridgeData(js2bridgeInterfaceElement, declaredType).apply {
            compileContext.log.debug("Js2JBridge process %s:%s", js2bridgeInterfaceElement.toString(), this)
        }
    }
}
