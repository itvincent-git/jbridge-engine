package net.jbridge.compiler.processor

import com.google.auto.common.BasicAnnotationProcessor
import com.google.common.collect.SetMultimap
import net.jbridge.annotation.JBridge
import net.jbridge.compiler.common.CompilerContext
import net.jbridge.compiler.writer.JBridge2JsInterfaceWriter
import net.jbridge.compiler.writer.JBridgeClassWriter
import net.jbridge.util.JavaxUtil
import java.util.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element

/**
 * Processor 入口类
 * Created by zhongyongsheng on 2018/4/13.
 */
class JBridgeProcessor : BasicAnnotationProcessor() {
    internal var portContext: CompilerContext? = null
    override fun initSteps(): Iterable<BasicAnnotationProcessor.ProcessingStep> {
        portContext = CompilerContext(processingEnv)
        CompilerContext.defaultIntance = portContext
        return listOf(JBridgeProcessingStep(portContext!!))
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    internal inner class JBridgeProcessingStep(context: CompilerContext) : JBridgeProcessing() {

        init {
            compilerContext = context
        }

        override fun annotations(): Set<Class<out Annotation>> {
            val set = HashSet<Class<out Annotation>>()
            set.add(JBridge::class.java)
            return set
        }

        override fun process(elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>): Set<Element> {
            val classSet = elementsByAnnotation.get(JBridge::class.java)
            classSet.map { element ->
                JBridgeInterfaceProcessor(compilerContext!!, JavaxUtil.toTypeElement(element)).process()
            }
            .forEach {
                try {
                    JBridgeClassWriter(it).write(processingEnv)
                    it.jBridge2JsMethods.forEach {
                        JBridge2JsInterfaceWriter(it.js2JBridgeData).write(processingEnv)
                    }

                } catch (e: Throwable) {
                    portContext!!.log.error("JBridgeProcessingStep error", e)
                }
            }

            return HashSet()
        }
    }

    internal abstract inner class JBridgeProcessing : BasicAnnotationProcessor.ProcessingStep {
        var compilerContext: CompilerContext? = null
    }
}
