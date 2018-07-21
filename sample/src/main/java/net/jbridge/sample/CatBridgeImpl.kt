package net.jbridge.sample

import android.widget.Toast

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
class CatBridgeImpl: CatBridgeInterface {
    override fun callCat(int: Int, str: String, long: Long) {
        Toast.makeText(SampleApplication.application, "callCat $int $str $long", Toast.LENGTH_SHORT).show()
    }
}