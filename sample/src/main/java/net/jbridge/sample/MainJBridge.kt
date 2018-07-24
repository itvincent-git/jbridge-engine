package net.jbridge.sample

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import net.jbridge.BaseJBridge
import net.jbridge.JBridgeBuilder
import net.jbridge.annotation.JBridge
import net.jbridge.annotation.JBridgeMethod
import net.jbridge.common.JBridgeCallback
import net.jbridge.sample.interfacepack.*

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@JBridge
abstract class MainJBridge: BaseJBridge() {
    var dog: DogBridgeInterface = DogBridgeImpl()
    var cat: CatBridgeInterface = CatBridgeImpl()

    @JBridgeMethod
    fun getDogBridge(): DogBridgeInterface {
        return dog
    }

    @JBridgeMethod
    fun getCatBridge(): CatBridgeInterface {
        return cat
    }

    abstract fun getToJsInterface(): ToJsInterface

    companion object {
        @JvmStatic
        fun newInstance(activity: Activity?, supportFragment: Fragment?, view: View?): MainJBridge {
            return JBridgeBuilder.newBuilder(MainJBridge::class.java)
                    .activity(activity)
                    .supportFragment(supportFragment)
                    .view(view)
                    .build()

        }
    }
}