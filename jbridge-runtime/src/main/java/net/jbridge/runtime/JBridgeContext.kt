package net.jbridge.runtime

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import java.lang.ref.WeakReference

/**
 * JBridge上下文，保存activity, fragment, view
 * Created by zhongyongsheng on 2018/7/21.
 */
class JBridgeContext<T : BaseJBridge>(activity: Activity?, supportFragment: Fragment?, view: View?, val jBridge: T) {
    val activityRef: WeakReference<Activity?>
    val supportFragmentRef: WeakReference<Fragment?>
    val viewRef: WeakReference<View?>

    init {
        activityRef = WeakReference(activity)
        supportFragmentRef = WeakReference(supportFragment)
        viewRef = WeakReference(view)
    }
}