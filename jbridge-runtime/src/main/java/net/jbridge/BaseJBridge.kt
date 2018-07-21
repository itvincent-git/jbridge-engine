package net.jbridge

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import net.jbridge.common.JBridgeCallback
import java.lang.ref.WeakReference

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
abstract class BaseJBridge {
    lateinit var activityRef: WeakReference<Activity?>
    lateinit var supportFragmentRef: WeakReference<Fragment?>
    lateinit var viewRef: WeakReference<View?>
    lateinit var callback : JBridgeCallback

    fun init(activity: Activity?, supportFragment: Fragment?, view: View?, cb: JBridgeCallback) {
        activityRef = WeakReference(activity)
        supportFragmentRef = WeakReference(supportFragment)
        viewRef = WeakReference(view)
        callback = cb
    }
}