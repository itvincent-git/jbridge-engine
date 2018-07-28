package net.jbridge.common

/**
 * JBridge2Js时调用的方法
 * Created by zhongyongsheng on 2018/7/21.
 */
interface JBridgeCallback {

    /**
     * 调用游戏的方法
     */
    fun eval(eval: String)
}