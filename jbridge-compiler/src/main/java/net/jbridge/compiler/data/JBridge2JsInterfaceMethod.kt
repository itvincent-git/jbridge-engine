package net.jbridge.compiler.data

import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.VariableElement

/**
 * 保存@JBridge2Js接口下定义的“方法”数据
 * Created by zhongyongsheng on 2018/7/25.
 */
class JBridge2JsInterfaceMethod(val executableElement: ExecutableElement,
                                val parameters: List<out VariableElement>) {
}