package net.jbridge.sample.interfacepack

import android.widget.Toast
import net.jbridge.sample.SampleApplication

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
class DogBridgeImpl: DogBridgeInterface {

    override fun callDog(index: Int, name: String, times: Long) {
        Toast.makeText(SampleApplication.application, "callDog $index $name $times", Toast.LENGTH_SHORT).show()
    }
}