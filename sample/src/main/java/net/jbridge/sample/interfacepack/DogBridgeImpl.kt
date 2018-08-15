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

    override fun callDog(index: Int, name: String) {
        Toast.makeText(SampleApplication.application, "callDog $index $name", Toast.LENGTH_SHORT).show()

    }

    override fun callDogWithReturn(index: Int, name: String, times: Long): Int {
        var i = index + 100
        Toast.makeText(SampleApplication.application, "callDogWithReturn $index return:$i", Toast.LENGTH_SHORT).show()
        return i
    }
}