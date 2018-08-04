package net.jbridge.compiler.processor


import net.jbridge.annotation.Js2JBridge
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridge2JsGetMethod
import net.jbridge.compiler.data.Js2JBridgeData
import net.jbridge.util.JavaxUtil
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * 处理@Js2JBridge接口类的信息
 * Created by zhongyongsheng on 2018/4/14.
 */
class Js2JBridgeProcessor internal constructor(val compileContext: CompilerContext,
                                               val js2bridgeInterfaceElement: TypeElement,
                                               val jBridge2JsMethods: List<JBridge2JsGetMethod>) {


    internal fun process(): Js2JBridgeData {

        if (js2bridgeInterfaceElement.getAnnotation(Js2JBridge::class.java) == null) {
            compileContext.log.error(js2bridgeInterfaceElement, "${js2bridgeInterfaceElement.getSimpleName()} must has @Js2JBridge")
        }
        if (js2bridgeInterfaceElement.getKind() != ElementKind.INTERFACE) {
            compileContext.log.error(js2bridgeInterfaceElement, "${js2bridgeInterfaceElement.getSimpleName()} must be interface")
        }

        val declaredType = JavaxUtil.asDeclared(js2bridgeInterfaceElement)

        val interfaceMethods = JavaxUtil.getAllMembers(compileContext.processingEnvironment, js2bridgeInterfaceElement)
                .filter { it.getModifiers().contains(Modifier.ABSTRACT) && it.getKind().equals(ElementKind.METHOD) }
                .map { JavaxUtil.asExecutable(it) }
                .map { Js2JBridgeInterfaceMethodProcessor(compileContext, it, jBridge2JsMethods).process() }

        return Js2JBridgeData(js2bridgeInterfaceElement, declaredType, interfaceMethods).apply {
            //compileContext.log.debug("Js2JBridge process %s:%s", js2bridgeInterfaceElement.toString(), this)
        }
    }
}
