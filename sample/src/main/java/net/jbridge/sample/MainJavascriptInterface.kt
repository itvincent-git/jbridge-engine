package net.jbridge.sample

import net.jbridge.BaseJBridge
import net.jbridge.JBridgeBuilder
import net.jbridge.annotation.JBridgeMethod

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
abstract class MainJavascriptInterface: BaseJBridge() {


    @JBridgeMethod
    fun getDogBridge(): DogBridgeInterface {
        return DogBridgeImpl()
    }

    companion object {
        @JvmStatic
        val instance: MainJavascriptInterface by lazy {
            JBridgeBuilder.newBuilder(MainJavascriptInterface::class.java).build()
        }
    }
}