package net.jbridge.sample.interfacepack

import net.jbridge.runtime.JBridgeContext
import net.jbridge.annotation.Js2JBridge
import net.jbridge.sample.MainJBridge

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@Js2JBridge
interface CatBridgeInterface {

    fun callCat(bridgeContext: JBridgeContext, toJsInterface: ToJsInterface, index : Int, name: String, times: Long)

    fun getCatCount(type: String)

    fun bytesTest(byteArray: ByteArray)
}