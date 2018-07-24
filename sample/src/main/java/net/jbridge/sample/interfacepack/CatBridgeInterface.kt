package net.jbridge.sample.interfacepack

import net.jbridge.JBridgeContext
import net.jbridge.annotation.Js2JBridge
import net.jbridge.sample.MainJBridge

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@Js2JBridge
interface CatBridgeInterface {

    fun callCat(bridgeContext: JBridgeContext<MainJBridge>, index : Int, name: String, times: Long)
}