package net.jbridge.compiler.common

import java.io.PrintWriter
import java.io.StringWriter
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.tools.Diagnostic

/**
 * messager日志封装
 * Created by zhongyongsheng on 2018/4/14.
 */
class Log(private val messager: Messager) {

    fun debug(element: Element, msg: String, vararg args: Any) {
        messager.printMessage(Diagnostic.Kind.NOTE, safeFormat(msg, *args), element)
    }

    fun debug(msg: String, vararg args: Any) {
        messager.printMessage(Diagnostic.Kind.NOTE, safeFormat(msg, *args))
    }

    fun warn(element: Element, msg: String, vararg args: Any) {
        messager.printMessage(Diagnostic.Kind.WARNING, safeFormat(msg, *args), element)
    }

    fun warn(msg: String, vararg args: Any) {
        messager.printMessage(Diagnostic.Kind.WARNING, safeFormat(msg, *args))
    }

    fun error(element: Element, msg: String, vararg args: Any) {
        messager.printMessage(Diagnostic.Kind.ERROR, safeFormat(msg, *args), element)
    }

    fun error(msg: String, throwable: Throwable) {
        val sw = StringWriter();
        throwable.printStackTrace(PrintWriter(sw))
        messager.printMessage(Diagnostic.Kind.ERROR, msg + ":" + sw.toString())
    }

    private fun safeFormat(msg: String, vararg args: Any): String {
        try {
            return java.lang.String.format(msg, *args)
        } catch (e: Exception) {
            return msg
        }

    }
}
