package net.jbridge.sample

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import net.jbridge.annotation.JBridge
import net.jbridge.annotation.JBridgeField
import net.jbridge.annotation.JBridgeMethod
import net.jbridge.common.JBridgeCallback
import net.jbridge.runtime.BaseJBridge
import net.jbridge.runtime.JBridgeBuilder
import net.jbridge.sample.interfacepack.*

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@JBridge
abstract class OtherJBridge: BaseJBridge() {

    @JvmField
    @JBridgeField
    protected var dog: DogBridgeInterface = DogBridgeImpl()
    @JvmField
    @JBridgeField
    protected var cat: CatBridgeInterface = CatBridgeImpl()

    @JBridgeMethod
    abstract fun getToJsInterface(): ToJsInterface

    companion object {
        @JvmStatic
        fun newInstance(activity: Activity?, supportFragment: Fragment?, view: View?, callback: JBridgeCallback): OtherJBridge {
            return JBridgeBuilder.newBuilder(OtherJBridge::class.java)
                    .activity(activity)
                    .supportFragment(supportFragment)
                    .view(view)
                    .build()
                    .apply {
                        jBridgeCallback = callback
                    }

        }
    }
}