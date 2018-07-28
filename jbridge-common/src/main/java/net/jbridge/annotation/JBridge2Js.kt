package net.jbridge.annotation

import net.jbridge.common.JBridgeCallback
import kotlin.reflect.KClass

/**
 * 标注在JBridge2Js的接口类上
 * Created by zhongyongsheng on 2018/7/20.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class JBridge2Js(val callbackClass: KClass<out JBridgeCallback>)