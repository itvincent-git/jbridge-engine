package net.jbridge.compiler.processor


import net.jbridge.annotation.JBridge2Js
import net.jbridge.annotation.JBridgeMethod
import net.jbridge.annotation.Js2JBridge
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridgeData
import net.jbridge.compiler.data.Js2JBridgeGetMethod
import net.jbridge.util.Util
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

/**
 * 处理@JBridge标注的类的信息
 * Created by zhongyongsheng on 2018/4/14.
 */
class JBridgeInterfaceProcessor internal constructor(internal var compileContext: CompilerContext,
                                                     internal var classElement: TypeElement) {


    internal fun process(): JBridgeData {
        val allMembers = Util.getAllMembers(compileContext.processingEnvironment, classElement)
        val js2BridgeMethods = allMembers
                .filter { element ->
                    element.kind == ElementKind.METHOD && element.getAnnotation(JBridgeMethod::class.java) != null
                }
                .map {
                    //compileContext.log.debug("Js2JBridge method %s", it.toString())
                    var excutableElement = Util.asExecutable(it)
                    val bridgeInterface = Util.toTypeElement(excutableElement.returnType)

                    if (bridgeInterface.getAnnotation(Js2JBridge::class.java) != null) {
                        val js2bridgeData = Js2JBridgeProcessor(compileContext, bridgeInterface).process()
                        val js2JBridgeGetMethod = Js2JBridgeGetMethod(excutableElement, excutableElement.simpleName.toString(), js2bridgeData)
                    } else if (bridgeInterface.getAnnotation(JBridge2Js::class.java) != null) {

                    }

                }

        return JBridgeData(classElement).apply {
            compileContext.log.debug("JBridge process %s:%s", classElement.toString(), this)
        }
    }
}
