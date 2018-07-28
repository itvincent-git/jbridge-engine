package net.jbridge.compiler.processor


import net.jbridge.annotation.JBridgeField
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
                    element.kind == ElementKind.FIELD && element.getAnnotation(JBridgeField::class.java) != null
                }
                .map {
                    compileContext.log.debug("Js2JBridge field %s", it.toString())
                    //val excutableElement = Util.asExecutable(it)
                    val variableElement = Util.toVariableElement(it)
                    val bridgeInterface = Util.toTypeElement(variableElement.asType())
                    variableElement to bridgeInterface
                }
                .filter {
                    it.second.getAnnotation(Js2JBridge::class.java) != null
                }
                .map {
                    val js2bridgeData = Js2JBridgeProcessor(compileContext, it.second).process()
                    Js2JBridgeGetMethod(it.first, it.first.simpleName.toString(), js2bridgeData)
                }

//        val jBridge2JsMethods = allMembers
//                .filter { element ->
//                    element.kind == ElementKind.METHOD && element.getAnnotation(JBridgeMethod::class.java) != null
//                }
//                .map {
//                    //compileContext.log.debug("Js2JBridge method %s", it.toString())
//                    val excutableElement = Util.asExecutable(it)
//                    val bridgeInterface = Util.toTypeElement(excutableElement.returnType)
//                    excutableElement to bridgeInterface
//                }
//                .filter {
//                    it.second.getAnnotation(JBridge2Js::class.java) != null
//                }
//                .map {
//                    val js2bridgeData = Js2JBridgeProcessor(compileContext, it.second).process()
//                    Js2JBridgeGetMethod(it.first, it.first.simpleName.toString(), js2bridgeData)
//                }

        return JBridgeData(classElement, js2BridgeMethods).apply {
            compileContext.log.debug("JBridge process %s:%s", classElement.toString(), this)
        }
    }
}
