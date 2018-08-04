package net.jbridge.compiler.data

import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

/**
 * 保存@Js2JBridge接口下定义的“方法”的参数
 * Created by zhongyongsheng on 2018/7/25.
 */
class Js2JBridgeInterfaceMethodParameter(val variableElement: VariableElement,
                                         val isJBridgeContext: Boolean,
                                         val isJBridgeToJsInterface: Boolean,
                                         val jBridgeToJsGetMethod: JBridge2JsGetMethod?) {
    override fun toString(): String {
        return "Js2JBridgeInterfaceMethodParameter(variableElement=$variableElement, isJBridgeContext=$isJBridgeContext, isJBridgeToJsInterface=$isJBridgeToJsInterface)"
    }

}