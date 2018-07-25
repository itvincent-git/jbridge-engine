package net.jbridge.compiler.data

import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType

/**
 * Created by zhongyongsheng on 2018/7/25.
 */
class Js2JBridgeData(val element:TypeElement, val declaredType: DeclaredType/*, List<PortMethod> methods*/) {

    var implTypeName: ClassName
    var typeName: ClassName

    init {
        typeName = ClassName.get(element)
        val implClassName = typeName.simpleNames().joinToString("_") + "_Impl"
        implTypeName = ClassName.get(typeName.packageName(), implClassName)
    }

    override fun toString(): String {
        return "Js2JBridgeData(element=$element, declaredType=$declaredType, implTypeName=$implTypeName, typeName=$typeName)"
    }

}