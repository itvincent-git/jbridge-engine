package net.jbridge.compiler.processor


import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.data.JBridge2JsInterfaceMethod
import javax.lang.model.element.ExecutableElement

/**
 * 处理@JBridge2Js标注的类的信息
 * Created by zhongyongsheng on 2018/4/14.
 */
class JBridge2JsInterfaceMethodProcessor internal constructor(val compileContext: CompilerContext,
                                                              val executableElement: ExecutableElement) {


    internal fun process(): JBridge2JsInterfaceMethod {
        val parameters = executableElement.getParameters()
        return JBridge2JsInterfaceMethod(executableElement, parameters)
    }
}
