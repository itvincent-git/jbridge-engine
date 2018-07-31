package net.jbridge.compiler.data

import javax.lang.model.element.ExecutableElement

/**
 * 保存@JBridgeMethod标注的字段信息
 * Created by zhongyongsheng on 2018/7/25.
 */
class JBridge2JsGetMethod(val element: ExecutableElement, val name: String, val js2JBridgeData: JBridge2JsData ) {

    override fun toString(): String {
        return "JBridge2JsGetMethod(element=$element, name='$name', js2JBridgeData=$js2JBridgeData)"
    }
}