package net.jbridge.compiler.data

import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

/**
 * 保存@Js2JBridge接口下定义的“方法”数据
 * Created by zhongyongsheng on 2018/7/25.
 */
class Js2JBridgeInterfaceMethod(val executableElement: ExecutableElement,
                                val parameters: List<out VariableElement>,//已经排除掉jbridgeContext
                                val hasJBridgeContext: Boolean,
                                val jBridgeContextGenericType: TypeMirror?/*JBridgeContext上的泛型类型*/) {
}