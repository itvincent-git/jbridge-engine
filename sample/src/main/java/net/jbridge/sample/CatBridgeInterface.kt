package net.jbridge.sample

import net.jbridge.annotation.Js2JBridge

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@Js2JBridge
interface CatBridgeInterface {

    fun callCat(index : Int, name: String, times: Long)
}