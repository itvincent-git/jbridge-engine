package net.jbridge.sample.interfacepack

import android.app.Activity
import android.widget.Toast
import net.jbridge.common.JBridgeCallback
import net.jbridge.sample.SampleApplication

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
class CatBridgeImpl: CatBridgeInterface {

    override fun callCat(activity: Activity?, callback: JBridgeCallback, index : Int, name: String, times: Long) {
        Toast.makeText(SampleApplication.application, "callCat $index $name $times", Toast.LENGTH_SHORT).show()
        callback.eval("javascript:onCallback('cat callback')")

    }
}