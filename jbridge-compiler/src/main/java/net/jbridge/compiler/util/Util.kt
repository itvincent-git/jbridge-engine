package net.jbridge.util

import com.google.auto.common.AnnotationMirrors
import com.google.auto.common.MoreElements
import com.google.auto.common.MoreTypes
import com.squareup.javapoet.TypeName

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.SimpleAnnotationValueVisitor6

/**
 * javax类型的转换工具
 * Created by zhongyongsheng on 2018/4/14.
 */
object Util {

    private val TO_TYPE = object : SimpleAnnotationValueVisitor6<TypeMirror, Void>() {
        override fun visitType(typeMirror: TypeMirror, aVoid: Void?): TypeMirror {
            return typeMirror
        }

        override fun defaultAction(o: Any, aVoid: Void?): TypeMirror {
            throw TypeNotPresentException(o.toString(), null)
        }
    }

    /**
     * Element转化为TypeElement，包含类的名称，实现的接口，父类等
     * @param element
     * @return
     */
    fun toTypeElement(element: Element): TypeElement {
        return MoreElements.asType(element)
    }

    /**
     * 转化为可执行方法的Element，包含方法的参数、返回值
     * @param element
     * @return
     */
    fun asExecutable(element: Element): ExecutableElement {
        return MoreElements.asExecutable(element)
    }

    /**
     * TypeMirror转化为TypeElement，包含类的名称，实现的接口，父类等
     * @param typeMirror
     * @return
     */
    fun toTypeElement(typeMirror: TypeMirror): TypeElement {
        return MoreTypes.asTypeElement(typeMirror)
    }

    /**
     * Element转化为DeclaredType，包含类型参数
     * @param element
     * @return
     */
    fun asDeclared(element: Element): DeclaredType {
        return MoreTypes.asDeclared(element.asType())
    }

    /**
     * TypeMirror转化为DeclaredType，包含类型参数
     */
    fun asDeclared(typeMirror: TypeMirror): DeclaredType {
        return MoreTypes.asDeclared(typeMirror)
    }

    /**
     * AnnotationMirror 包含注解的类型，注解元素的值
     * @param element
     * @param annotationClass
     * @return
     */
    fun getAnnotationMirror(element: Element, annotationClass: Class<out Annotation>): AnnotationMirror {
        return MoreElements.getAnnotationMirror(element, annotationClass).get()
    }

    /**
     * 根据元素名称返回注解的元素的值
     * @param annotationMirror
     * @param name
     * @return
     */
    fun getAnnotationValue(annotationMirror: AnnotationMirror, name: String): AnnotationValue {
        return AnnotationMirrors.getAnnotationValue(annotationMirror, name)
    }

    /**
     * 返回所有成员变量及方法
     * @param processingEnvironment
     * @param element
     * @return
     */
    fun getAllMembers(processingEnvironment: ProcessingEnvironment, element: TypeElement): List<Element> {
        return processingEnvironment.elementUtils.getAllMembers(element)
    }

    /**
     * 将AnnotationValue转成TypeMirror，TypeMirror带有类型信息
     */
    fun annotationValueToType(annotationValue: AnnotationValue): TypeMirror {
        return TO_TYPE.visit(annotationValue)
    }

    /**
     * TypeMirror转成javapoet的TypeName
     */
    fun typeMirrorToTypeName(typeMirror: TypeMirror): TypeName {
        return TypeName.get(typeMirror)
    }
}
