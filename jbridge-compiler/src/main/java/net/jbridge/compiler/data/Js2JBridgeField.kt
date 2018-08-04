package net.jbridge.compiler.data

import javax.lang.model.element.VariableElement

/**
 * 保存@JBridgeField标注的字段信息
 * Created by zhongyongsheng on 2018/7/25.
 */
class Js2JBridgeField(val element: VariableElement,
                      val name: String,
                      val js2JBridgeData: Js2JBridgeData ) {

    override fun toString(): String {
        return "Js2JBridgeField(element=$element, name='$name', jBridge2JsData=$js2JBridgeData)"
    }
}