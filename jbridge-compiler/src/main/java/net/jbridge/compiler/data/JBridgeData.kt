package net.jbridge.compiler.data

import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement

/**
 * Created by zhongyongsheng on 2018/7/25.
 */
class JBridgeData(element: TypeElement) {

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