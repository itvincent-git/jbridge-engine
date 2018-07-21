package net.jbridge.sample

import android.widget.Toast

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
class CatBridgeImpl: CatBridgeInterface {

    override fun callCat(index : Int, name: String, times: Long) {
        Toast.makeText(SampleApplication.application, "callCat $index $name $times", Toast.LENGTH_SHORT).show()
    }
}