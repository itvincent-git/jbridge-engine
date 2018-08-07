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
import net.jbridge.util.JavaxUtil
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * 处理@JBridge标注的类的信息
 * Created by zhongyongsheng on 2018/4/14.
 */
class JBridgeInterfaceProcessor internal constructor(internal var compileContext: CompilerContext,
                                                     internal var classElement: TypeElement) {


    internal fun process(): JBridgeData {
        val allMembers = JavaxUtil.getAllMembers(compileContext.processingEnvironment, classElement)

        //jbridgeCallback字段
        val jBridgeCallbackField = allMembers
                .filter { element ->
                    element.kind == ElementKind.FIELD && element.getAnnotation(JBridgeField::class.java) != null
                }
                .map { JavaxUtil.toVariableElement(it) }
                .filter { JavaxUtil.toTypeElement(it.asType()).qualifiedName.toString() == JBridgeCallback::class.java.name }
                .firstOrNull()

        //getXXXInterface方法
        val jBridge2JsMethods = allMembers
                .filter { element ->
                    element.kind == ElementKind.METHOD && element.getAnnotation(JBridgeMethod::class.java) != null
                }
                .map {
                    val excutableElement = JavaxUtil.asExecutable(it)
                    val bridgeInterface = JavaxUtil.toTypeElement(excutableElement.returnType)
                    excutableElement to bridgeInterface
                }
                .filter {
                    it.second.getAnnotation(JBridge2Js::class.java) != null
                }
                .map {
                    val jbridge2Js = JBridge2JsProcessor(compileContext, it.second, classElement/*JBridgeClass*/).process()
                    JBridge2JsGetMethod(it.first, it.first.simpleName.toString(), jbridge2Js)
                }

        //var XXXInterface
        val js2BridgeFields = allMembers
                .filter { element ->
                    element.kind == ElementKind.FIELD && element.getAnnotation(JBridgeField::class.java) != null
                }
                .map {
                    val variableElement = JavaxUtil.toVariableElement(it)
                    val bridgeInterface = JavaxUtil.toTypeElement(variableElement.asType())
                    variableElement to bridgeInterface
                }
                .filter {
                    it.second.getAnnotation(Js2JBridge::class.java) != null
                }
                .map {
                    val js2bridgeData = Js2JBridgeProcessor(compileContext, it.second, jBridge2JsMethods).process()
                    Js2JBridgeField(it.first, it.first.simpleName.toString(), js2bridgeData)
                }

        val overrideMethods = allMembers
                .filter({ element -> element.modifiers.contains(Modifier.ABSTRACT) && element.kind == ElementKind.METHOD })
                .map {
                    JavaxUtil.asExecutable(it)
                }
                .filter {
                    val name = it.simpleName.toString()
                    name == "onJsToBridge" || name == "onJsToBridgeSync" || name == "getInjectJs"
                }
                .map { it.simpleName.toString() to it }
                .toMap()

        return JBridgeData(classElement,
                js2BridgeFields,
                jBridge2JsMethods,
                jBridgeCallbackField,
                overrideMethods["onJsToBridge"],
                overrideMethods["onJsToBridgeSync"],
                overrideMethods["getInjectJs"]).apply {
            compileContext.log.debug("JBridge process %s:%s", classElement.toString(), this)
        }
    }
}
