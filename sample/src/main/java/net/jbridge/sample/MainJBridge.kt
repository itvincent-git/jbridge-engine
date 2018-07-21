package net.jbridge.sample

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import net.jbridge.BaseJBridge
import net.jbridge.JBridgeBuilder
import net.jbridge.annotation.JBridge
import net.jbridge.annotation.JBridgeMethod
import net.jbridge.common.JBridgeCallback
import net.jbridge.sample.interfacepack.CatBridgeImpl
import net.jbridge.sample.interfacepack.CatBridgeInterface
import net.jbridge.sample.interfacepack.DogBridgeImpl
import net.jbridge.sample.interfacepack.DogBridgeInterface

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
        fun newInstance(activity: Activity?, supportFragment: Fragment?, view: View?, callback: JBridgeCallback): MainJBridge {
            return JBridgeBuilder.newBuilder(MainJBridge::class.java, callback)
                    .activity(activity)
                    .supportFragment(supportFragment)
                    .view(view)
                    .build()
        }
    }
}