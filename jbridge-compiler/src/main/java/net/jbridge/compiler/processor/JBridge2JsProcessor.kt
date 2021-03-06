package net.jbridge.compiler.processor


import net.jbridge.annotation.JBridge2Js
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridge2JsData
import net.jbridge.util.JavaxUtil
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * 处理@JBridge2Js接口类的信息
 * Created by zhongyongsheng on 2018/4/14.
 */
class JBridge2JsProcessor internal constructor(val compileContext: CompilerContext,
                                               val jbridge2JsInterfaceElement: TypeElement,
                                               val jBridgeClassElement: TypeElement) {


    internal fun process(): JBridge2JsData {

        if (jbridge2JsInterfaceElement.getAnnotation(JBridge2Js::class.java) == null) {
            compileContext.log.error(jbridge2JsInterfaceElement, "${jbridge2JsInterfaceElement.getSimpleName()} must has @JBridge2Js")
        }
        if (jbridge2JsInterfaceElement.getKind() != ElementKind.INTERFACE) {
            compileContext.log.error(jbridge2JsInterfaceElement, "${jbridge2JsInterfaceElement.getSimpleName()} must be interface")
        }

        val declaredType = JavaxUtil.asDeclared(jbridge2JsInterfaceElement)

        val interfaceMethods = JavaxUtil.getAllMembers(compileContext.processingEnvironment, jbridge2JsInterfaceElement)
                .filter { it.getModifiers().contains(Modifier.ABSTRACT) && it.getKind().equals(ElementKind.METHOD) }
                .map { JavaxUtil.asExecutable(it) }
                .map { JBridge2JsInterfaceMethodProcessor(compileContext, it).process() }

        return JBridge2JsData(jbridge2JsInterfaceElement, declaredType, interfaceMethods, jBridgeClassElement).apply {
            //compileContext.log.debug("JBridge2Js process %s:%s", jbridge2JsInterfaceElement.toString(), this)
        }
    }
}
