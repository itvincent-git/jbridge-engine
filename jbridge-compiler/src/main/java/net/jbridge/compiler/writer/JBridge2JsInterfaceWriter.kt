package net.jbridge.compiler.writer

import com.squareup.javapoet.*
import net.jbridge.common.JBridgeCallback
import net.jbridge.compiler.data.JBridge2JsData
import net.jbridge.compiler.data.JBridge2JsInterfaceMethod
import net.jbridge.util.JavaxUtil
import net.jbridge.util.TmpVar
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeMirror

/**
 * 生成XXXInterface_Impl
 * Created by zhongyongsheng on 2018/4/14.
 */
class JBridge2JsInterfaceWriter(val jbridge2jsData: JBridge2JsData) : JBridgeBaseWriter(jbridge2jsData.implTypeName) {

    override fun createTypeSpecBuilder(): TypeSpec.Builder {
        val builder = TypeSpec.classBuilder(jbridge2jsData.implTypeName)
        builder.addModifiers(Modifier.PUBLIC)
                .addSuperinterface(jbridge2jsData.typeName)
        val fieldName = addJBridgeCallbackField(builder)
        addConstructor(builder, fieldName)
        addJBridge2JsMethod(builder, fieldName)
        return builder
    }

    /**
     * 生成callback field
     */
    private fun addJBridgeCallbackField(builder: TypeSpec.Builder): String {
        val tmpVar = TmpVar()
        val name = JBridgeCallback::class.java.simpleName.decapitalize()
        val fieldName = tmpVar.getTmpVar("_$name")

        val field = FieldSpec.builder(JBridgeCallback::class.java,
                fieldName,
                Modifier.PRIVATE)
                .build()
        builder.addField(field)
        return fieldName
    }

    /**
     * 生成构造函数
     */
    private fun addConstructor(builder: TypeSpec.Builder, fieldName: String) {
        MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(TypeName.get(JBridgeCallback::class.java), "callback").build())
                .addStatement("\$L = callback", fieldName)
                .apply {
                    builder.addMethod(this.build())
                }
    }

    /**
     * 生成接口里定义的JBridge2Js方法
     */
    private fun addJBridge2JsMethod(builder: TypeSpec.Builder, fieldName: String) {
        jbridge2jsData.methods.forEach { interfaceMethod ->
            val executableElement = interfaceMethod.executableElement
            MethodSpec.methodBuilder(executableElement.simpleName.toString())
                    .addModifiers(Modifier.PUBLIC)
                    .apply {
                        addJBridge2JsMethodInner(this, fieldName, executableElement, interfaceMethod)
                        interfaceMethod.parameters.forEach {
                            this.addParameter(ParameterSpec.get(it))
                        }
                        builder.addMethod(this.build())
                    }

        }
    }

    private fun addJBridge2JsMethodInner(methodSpec: MethodSpec.Builder, fieldName: String,
                                         executableElement: ExecutableElement, interfaceMethod: JBridge2JsInterfaceMethod) {
        methodSpec.addStatement("\$L.eval(\"if(window.\$L) \$L(\$L)\")",
                fieldName,
                executableElement.simpleName.toString(),//methodName
                executableElement.simpleName.toString(),//methodName
                interfaceMethod.parameters.joinToString {
                    val type = it.asType()
                    if (type.kind.isPrimitive || isPrimitiveBoxer(type)) {//基本类型不加引号
                        "\" + ${it.simpleName} + \""
                    } else {
                        "\\\"\" + ${it.simpleName} + \"\\\""
                    }
                })//param list
    }

    private fun isPrimitiveBoxer(type: TypeMirror): Boolean {
        val typeName = JavaxUtil.toTypeElement(type).qualifiedName
        when {
            typeName.contentEquals("java.lang.Integer") -> return true
            typeName.contentEquals("java.lang.Long") -> return true
            typeName.contentEquals("java.lang.Boolean") -> return true
            typeName.contentEquals("java.lang.Float") -> return true
            typeName.contentEquals("java.lang.Double") -> return true
        }
        return false
    }

}
