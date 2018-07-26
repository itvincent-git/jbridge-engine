package net.jbridge.compiler.data

import com.squareup.javapoet.ClassName
import net.jbridge.util.Util
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType

/**
 * 保存@Js2JBridge接口下定义的“方法”数据
 * Created by zhongyongsheng on 2018/7/25.
 */
class Js2JBridgeInterfaceMethod(val executableElement: ExecutableElement,
                                val hasJBridgeContext: Boolean) {
//    lateinit var parameters : List<out VariableElement>

    init {

    }

}