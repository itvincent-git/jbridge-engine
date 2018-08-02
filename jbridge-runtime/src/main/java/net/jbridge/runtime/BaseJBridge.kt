package net.jbridge.runtime

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import net.jbridge.annotation.JBridgeField
import net.jbridge.common.JBridgeCallback

/**
 * 自定义的JBridge类要继承于此，拥有上下文
 * Created by zhongyongsheng on 2018/7/20.
 */
abstract class BaseJBridge : IJBridge{
    lateinit var bridgeContext: JBridgeContext<out IJBridge>

    //这个不能删除，实例对象后，传入到此，用于回调
    @JvmField
    @JBridgeField
    protected var jBridgeCallback: JBridgeCallback? = null

    fun init(activity: Activity?, supportFragment: Fragment?, view: View?) {
        bridgeContext = JBridgeContext(activity, supportFragment, view, this)
    }
}