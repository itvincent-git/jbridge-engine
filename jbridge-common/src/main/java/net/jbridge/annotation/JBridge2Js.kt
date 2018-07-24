package net.jbridge.annotation

import net.jbridge.common.JBridgeCallback
import kotlin.reflect.KClass

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class JBridge2Js(val callbackClass: KClass<out JBridgeCallback>)