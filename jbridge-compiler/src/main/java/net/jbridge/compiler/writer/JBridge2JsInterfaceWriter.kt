package net.jbridge.compiler.writer

import com.squareup.javapoet.*
import net.jbridge.common.JBridgeCallback
import net.jbridge.compiler.data.JBridge2JsData
import net.jbridge.compiler.data.JBridge2JsInterfaceMethod
import net.jbridge.util.TmpVar
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier

/**
 * 生成XXXInterface_Impl
 * Created by zhongyongsheng on 2018/4/14.
 */
class JBridge2JsInterfaceWriter(internal var jbridge2jsData: JBridge2JsData) : JBridgeBaseWriter(jbridge2jsData.implTypeName) {

    override fun createTypeSpecBuilder(): TypeSpec.Builder {
        val builder = TypeSpec.classBuilder(jbridge2jsData.implTypeName)
        builder.addModifiers(Modifier.PUBLIC)
                .addSuperinterface(jbridge2jsData.typeName)
        val fieldName = addJBridgeCallbackField(builder)
        addConstructor(builder, fieldName)
        addJBridge2JsMethod(builder, fieldName)
        //addJBridgeToJsMethod(builder)
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
        methodSpec.addStatement("\$L.eval(\"javascript:\$L(\$L)\")",
                fieldName,
                executableElement.simpleName.toString(),//methodName

                interfaceMethod.parameters.joinToString { "\\\"\" + ${it.simpleName} + \"\\\"" })//param list
    }

}
