package net.jbridge.compiler.processor


import net.jbridge.compiler.common.CompilerContext
import javax.lang.model.element.TypeElement

/**
 * Created by zhongyongsheng on 2018/4/14.
 */
class JBridgeInterfaceProcessor internal constructor(internal var compileContext: CompilerContext, internal var classElement: TypeElement) {


//    internal fun process(): ProtoQueueClassData {
//        val annotationMirror = Util.getAnnotationMirror(classElement, ProtoQueueClass::class.java)
//
//        val protoContextLiteral = Util.getAnnotationValue(annotationMirror, "protoContextLiteral").toString()
//        val toByteArrayLiteral = Util.getAnnotationValue(annotationMirror, "toByteArrayLiteral").toString()
//        val buildProtoLiteral = Util.getAnnotationValue(annotationMirror, "buildProtoLiteral").toString()
//        val uriLiteral = Util.getAnnotationValue(annotationMirror, "uriLiteral").toString()
//        val superClass = classElement.superclass
//        val declaredType = Util.asDeclared(superClass)
//        val typeArguments = declaredType.typeArguments
//
//        if (typeArguments.size < 2) {
//            compileContext.log.error(classElement, "must defined the type arguments <P,C>")
//        }
//
//        val allMembers = Util.getAllMembers(compileContext.processingEnvironment, classElement)
//        val overrideMethods = allMembers
//                .filter({ element -> element.modifiers.contains(Modifier.ABSTRACT) && element.kind == ElementKind.METHOD })
//                .map {
//                    Util.asExecutable(it)
//                }
//                .map { it.toString() to it }.toMap()
//
//        var data = ProtoQueueClassData(classElement,
//                protoContextLiteral.filter { it != '\"' },
//                buildProtoLiteral.filter { it != '\"' },
//                toByteArrayLiteral.filter { it != '\"' },
//                uriLiteral.filter { it != '\"' },
//                typeArguments[0],
//                typeArguments[1],
//                overrideMethods["buildProto(byte[])"],
//                overrideMethods["toByteArray(P)"],
//                overrideMethods["getProtoContext(P)"],
//                overrideMethods["getOwnAppId()"],
//                overrideMethods["incrementAndGetSeqContext()"],
//                overrideMethods["getSeqContext()"],
//                overrideMethods["getReceiveUri(P)"])
//        compileContext.log.debug("ProtoQueue process %s:%s", classElement.toString(), data)
//        return data
//    }
}
