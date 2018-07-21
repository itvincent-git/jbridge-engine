package net.jbridge.sample.interfacepack

import net.jbridge.annotation.Js2JBridge

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@Js2JBridge
interface DogBridgeInterface {

    fun callDog(index: Int, name: String, times: Long)
}