package net.jbridge.sample

import android.app.Application

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
class SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        @JvmStatic
        var application: SampleApplication? = null
    }
}