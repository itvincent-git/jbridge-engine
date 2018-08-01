package net.jbridge.sample.interfacepack

import net.jbridge.annotation.JBridge2Js
import net.jbridge.sample.callback.WebViewCallback

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@JBridge2Js
interface ToJsInterface {

    fun onCallback(value : String)
}