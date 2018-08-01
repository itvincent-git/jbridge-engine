package net.jbridge.runtime

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View

/**
 * 自定义的JBridge类要继承于此，拥有上下文
 * Created by zhongyongsheng on 2018/7/20.
 */
abstract class BaseJBridge : IJBridge{
    lateinit var bridgeContext: JBridgeContext<out IJBridge>

    fun init(activity: Activity?, supportFragment: Fragment?, view: View?) {
        bridgeContext = JBridgeContext(activity, supportFragment, view, this)
    }
}