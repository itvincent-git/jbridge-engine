package net.jbridge.compiler.processor


import net.jbridge.annotation.JBridge2Js
import net.jbridge.annotation.JBridgeField
import net.jbridge.annotation.JBridgeMethod
import net.jbridge.annotation.Js2JBridge
import net.jbridge.common.JBridgeCallback
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridge2JsGetMethod
import net.jbridge.compiler.data.JBridgeData
import net.jbridge.compiler.data.Js2JBridgeField
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
                    //compileContext.log.debug("Js2JBridge field %s", it.toString())
                    val variableElement = Util.toVariableElement(it)
                    val bridgeInterface = Util.toTypeElement(variableElement.asType())
                    variableElement to bridgeInterface
                }
                .filter {
                    it.second.getAnnotation(Js2JBridge::class.java) != null
                }
                .map {
                    val js2bridgeData = Js2JBridgeProcessor(compileContext, it.second).process()
                    Js2JBridgeField(it.first, it.first.simpleName.toString(), js2bridgeData)
                }

        val jBridgeCallbackField = allMembers
                .filter { element ->
                    element.kind == ElementKind.FIELD && element.getAnnotation(JBridgeField::class.java) != null
                }
                .map { Util.toVariableElement(it) }
                .filter { Util.toTypeElement(it.asType()).qualifiedName.toString() == JBridgeCallback::class.java.name }
                .firstOrNull()

        val jBridge2JsMethods = allMembers
                .filter { element ->
                    element.kind == ElementKind.METHOD && element.getAnnotation(JBridgeMethod::class.java) != null
                }
                .map {
                    //compileContext.log.debug("Js2JBridge method %s", it.toString())
                    val excutableElement = Util.asExecutable(it)
                    val bridgeInterface = Util.toTypeElement(excutableElement.returnType)
                    excutableElement to bridgeInterface
                }
                .filter {
                    it.second.getAnnotation(JBridge2Js::class.java) != null
                }
                .map {
                    val jbridge2Js = JBridge2JsProcessor(compileContext, it.second).process()
                    JBridge2JsGetMethod(it.first, it.first.simpleName.toString(), jbridge2Js)
                }

        return JBridgeData(classElement, js2BridgeMethods, jBridge2JsMethods, jBridgeCallbackField).apply {
            compileContext.log.debug("JBridge process %s:%s", classElement.toString(), this)
        }
    }
}
