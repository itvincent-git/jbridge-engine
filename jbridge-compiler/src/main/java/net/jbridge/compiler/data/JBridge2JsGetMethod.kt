package net.jbridge.compiler.data

import net.jbridge.util.JavaxUtil
import javax.lang.model.element.ExecutableElement

/**
 * 保存@JBridgeMethod标注的字段信息
 * Created by zhongyongsheng on 2018/7/25.
 */
class JBridge2JsGetMethod(val element: ExecutableElement,
                          val name: String,
                          val jBridge2JsData: JBridge2JsData) {
    val returnElement = JavaxUtil.toTypeElement(element.returnType)

    override fun toString(): String {
        return "JBridge2JsGetMethod(element=$element, name='$name', jBridge2JsData=$jBridge2JsData)"
    }
}