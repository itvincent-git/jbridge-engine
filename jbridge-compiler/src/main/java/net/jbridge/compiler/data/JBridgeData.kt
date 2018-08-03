package net.jbridge.compiler.data

import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * 保存@JBridge数据
 * Created by zhongyongsheng on 2018/7/25.
 */
class JBridgeData(val element: TypeElement,
                  val js2BridgeFields: List<Js2JBridgeField>,
                  val jBridge2JsMethods: List<JBridge2JsGetMethod>,
                  val jBridgeCallbackField: VariableElement?) {

    var implTypeName: ClassName
    var typeName: ClassName

    init {
        typeName = ClassName.get(element)
        val implClassName = typeName.simpleNames().joinToString("_") + "_Impl"
        implTypeName = ClassName.get(typeName.packageName(), implClassName)
    }

    override fun toString(): String {
        return "JBridgeData(implTypeName=$implTypeName, typeName=$typeName)"
    }


}