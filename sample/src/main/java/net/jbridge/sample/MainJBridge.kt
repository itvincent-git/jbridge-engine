package net.jbridge.sample

import net.jbridge.BaseJBridge
import net.jbridge.JBridgeBuilder
import net.jbridge.annotation.JBridge
import net.jbridge.annotation.JBridgeMethod

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@JBridge
abstract class MainJBridge: BaseJBridge() {


    @JBridgeMethod
    fun getDogBridge(): DogBridgeInterface {
        return DogBridgeImpl()
    }

    @JBridgeMethod
    fun getCatBridge(): CatBridgeInterface {
        return CatBridgeImpl()
    }

    companion object {
        @JvmStatic
        val LAZY_INSTANCE: MainJBridge by lazy {
            JBridgeBuilder.newBuilder(MainJBridge::class.java).build()
        }
    }
}