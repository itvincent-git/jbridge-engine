package net.jbridge.sample.interfacepack

import android.app.Activity
import net.jbridge.annotation.Js2JBridge
import net.jbridge.common.JBridgeCallback

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@Js2JBridge
interface CatBridgeInterface {

    fun callCat(activity: Activity?, callback: JBridgeCallback, index : Int, name: String, times: Long)
}