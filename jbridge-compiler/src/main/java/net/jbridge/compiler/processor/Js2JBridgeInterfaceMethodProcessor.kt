package net.jbridge.compiler.processor


import com.squareup.javapoet.TypeName
import net.jbridge.annotation.JBridge2Js
import net.jbridge.annotation.JBridgeMethod
import net.jbridge.annotation.Js2JBridge
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridgeData
import net.jbridge.compiler.data.Js2JBridgeGetMethod
import net.jbridge.compiler.data.Js2JBridgeInterfaceMethod
import net.jbridge.util.Util
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

/**
 * 处理@JBridge标注的类的信息
 * Created by zhongyongsheng on 2018/4/14.
 */
class Js2JBridgeInterfaceMethodProcessor internal constructor(internal var compileContext: CompilerContext,
                                                              internal var executableElement: ExecutableElement) {


    internal fun process(): Js2JBridgeInterfaceMethod {
        val parameters = executableElement.getParameters()

        val hasJBridgeContext = parameters.map { it.asType()}
                .filter { !it.kind.isPrimitive }
                .map { Util.toTypeElement(it) }
                .filter { it.qualifiedName.toString() == "net.jbridge.JBridgeContext" }
                .isNotEmpty()
//                .forEach {
                    //compileContext.log.debug("Js2JBridgeInterfaceMethodProcessor %s", it.qualifiedName)
//                }

        return Js2JBridgeInterfaceMethod(executableElement, hasJBridgeContext).apply {
            compileContext.log.debug("Js2JBridgeInterfaceMethod process %s:%s", executableElement.toString(), this)
        }
    }
}
