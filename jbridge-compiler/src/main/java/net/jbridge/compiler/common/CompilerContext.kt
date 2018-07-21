package net.jbridge.compiler.common

import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by zhongyongsheng on 2018/4/13.
 */
class CompilerContext(var processingEnvironment: ProcessingEnvironment) {
    var log: Log

    init {
        log = Log(processingEnvironment.messager)
    }

    companion object {

        var defaultIntance: CompilerContext? = null
    }
}
