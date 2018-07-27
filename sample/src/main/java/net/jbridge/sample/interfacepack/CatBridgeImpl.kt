package net.jbridge.sample.interfacepack

import android.widget.Toast
import net.jbridge.runtime.JBridgeContext
import net.jbridge.sample.MainJBridge
import net.jbridge.sample.SampleApplication

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
class CatBridgeImpl: CatBridgeInterface {

    override fun callCat(bridgeContext: JBridgeContext<MainJBridge>, index : Int, name: String, times: Long) {
        Toast.makeText(SampleApplication.application, "callCat $index $name $times", Toast.LENGTH_SHORT).show()
        // do something business
//        bridgeContext.jBridge.getToJsInterface().onCallback("cat callback")
    }

    override fun getCatCount(type: String) {

    }
}