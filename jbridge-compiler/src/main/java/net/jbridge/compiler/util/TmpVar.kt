package net.jbridge.util

import java.util.HashMap

/**
 * 临时变量生成
 * Created by zhongyongsheng on 2018/4/16.
 */
class TmpVar {

    private val tmpVarIndices = HashMap<String, Int>()

    fun getTmpVar(prefix: String): String {
        if (!prefix.startsWith("_"))
            throw IllegalArgumentException("tmp var prefix should start with _")
        if (prefix.startsWith("__"))
            throw IllegalArgumentException("tmp var prefix cannot use with __")
        var index: Int? = tmpVarIndices[prefix]
        if (index == null) index = 0
        val result = buildTmpVar(prefix, index)
        tmpVarIndices[prefix] = index + 1
        return result
    }

    private fun buildTmpVar(prefix: String, index: Int): String {
        return prefix + if (index == 0) "" else "_$index"

    }
}
