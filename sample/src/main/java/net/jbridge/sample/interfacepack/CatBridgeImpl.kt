package net.jbridge.sample.interfacepack

import android.widget.Toast
import net.jbridge.runtime.JBridgeContext
import net.jbridge.sample.MainJBridge
import net.jbridge.sample.SampleApplication

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
class CatBridgeImpl: CatBridgeInterface {

    override fun callCat(bridgeContext: JBridgeContext, toJsInterface: ToJsInterface, index : Int, name: String, times: Long) {
        Toast.makeText(SampleApplication.application, "callCat $index $name $times", Toast.LENGTH_SHORT).show()
        // do something business
        toJsInterface.invokeJs1("cat callback")
        toJsInterface.invokeJs2(1, 10000, true, "abc")
    }

    override fun getCatCount(type: String) {

    }

    override fun bytesTest(byteArray: ByteArray) {
        Toast.makeText(SampleApplication.application, "bytesTest ${byteArray.size}", Toast.LENGTH_SHORT).show()
    }
}