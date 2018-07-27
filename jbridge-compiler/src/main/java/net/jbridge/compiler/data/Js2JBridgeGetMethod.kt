package net.jbridge.compiler.data

import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement

/**
 * 保存@JBridgeMethod标注的方法的信息
 * Created by zhongyongsheng on 2018/7/25.
 */
class Js2JBridgeGetMethod(val element: ExecutableElement, val name: String, val js2JBridgeData: Js2JBridgeData ) {

    override fun toString(): String {
        return "Js2JBridgeGetMethod(element=$element, name='$name', js2JBridgeData=$js2JBridgeData)"
    }
}