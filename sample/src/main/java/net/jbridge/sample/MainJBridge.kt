package net.jbridge.sample

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import net.jbridge.annotation.JBridge
import net.jbridge.annotation.JBridgeField
import net.jbridge.common.JBridgeCallback
import net.jbridge.runtime.BaseJBridge
import net.jbridge.runtime.JBridgeBuilder
import net.jbridge.sample.interfacepack.CatBridgeImpl
import net.jbridge.sample.interfacepack.CatBridgeInterface
import net.jbridge.sample.interfacepack.DogBridgeImpl
import net.jbridge.sample.interfacepack.DogBridgeInterface

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
@JBridge
abstract class MainJBridge: BaseJBridge() {

    @JvmField
    @JBridgeField
    protected var dog: DogBridgeInterface = DogBridgeImpl()
    @JvmField
    @JBridgeField
    protected var cat: CatBridgeInterface = CatBridgeImpl()
    @JvmField
    @JBridgeField
    protected var jBridgeCallback: JBridgeCallback? = null

//    @JBridgeMethod
//    fun getDogBridge(): DogBridgeInterface {
//        return dog
//    }
//
//    @JBridgeMethod
//    fun getCatBridge(): CatBridgeInterface {
//        return cat
//    }
//
//    @JBridgeMethod
//    fun getJBridgeCallback(): JBridgeCallback? {
//        return jBridgeCallback
//    }

//    @JBridgeMethod
//    abstract fun getToJsInterface(): ToJsInterface

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