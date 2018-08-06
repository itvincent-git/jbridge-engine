package net.jbridge.compiler.data

import javax.lang.model.element.ExecutableElement

/**
 * 保存@Js2JBridge接口下定义的“方法”数据
 * Created by zhongyongsheng on 2018/7/25.
 */
class Js2JBridgeInterfaceMethod(val executableElement: ExecutableElement,
                                val parameters: List<Js2JBridgeInterfaceMethodParameter>) {
    val isReturnNotVoid = executableElement.returnType.toString() != "void"

    override fun toString(): String {
        return "Js2JBridgeInterfaceMethod(executableElement=$executableElement, parameters=$parameters)"
    }
}