package net.jbridge.compiler.data

import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType

/**
 * 保存@Js2JBridge 接口类的数据
 * Created by zhongyongsheng on 2018/7/25.
 */
class JBridge2JsData(val element:TypeElement, val declaredType: DeclaredType, val methods:List<JBridge2JsInterfaceMethod> ) {

    var implTypeName: ClassName
    var typeName: ClassName

    init {
        typeName = ClassName.get(element)
        val implClassName = typeName.simpleNames().joinToString("_") + "_Impl"
        implTypeName = ClassName.get(typeName.packageName(), implClassName)
    }

    override fun toString(): String {
        return "JBridge2JsData(element=$element, declaredType=$declaredType, methods=$methods, implTypeName=$implTypeName, typeName=$typeName)"
    }

}