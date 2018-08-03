package net.jbridge.sample.interfacepack

import net.jbridge.annotation.JBridge2Js

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@JBridge2Js
interface ToJsInterface {

    fun invokeJs1(value : String)

    fun invokeJs2(a: Int, b: Long, c: Boolean, d: String)

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    fun invokeJs2(a: java.lang.Integer, b: java.lang.Long, c: java.lang.Boolean, d: java.lang.String)
}