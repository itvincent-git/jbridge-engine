package net.jbridge

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import java.lang.ref.WeakReference

/**
 * Created by zhongyongsheng on 2018/7/21.
 */
class JBridgeContext<T : BaseJBridge>(activity: Activity?, supportFragment: Fragment?, view: View?, val jBridge: T/*, cb: JBridgeCallback*/) {
    val activityRef: WeakReference<Activity?>
    val supportFragmentRef: WeakReference<Fragment?>
    val viewRef: WeakReference<View?>
//    val callback : JBridgeCallback

    init {
        activityRef = WeakReference(activity)
        supportFragmentRef = WeakReference(supportFragment)
        viewRef = WeakReference(view)
//        callback = cb
    }
}