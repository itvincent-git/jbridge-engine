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
    /**
     * 上下文
     */
    lateinit var bridgeContext: JBridgeContext

    //这个不能删除，实例对象后，传入到此，用于回调
    @JvmField
    @JBridgeField
    protected var jBridgeCallback: JBridgeCallback? = null

    //internal
    internal fun init(activity: Activity?, supportFragment: Fragment?, view: View?) {
        bridgeContext = JBridgeContext(activity, supportFragment, view)
    }

    /**
     * js调用Bridge方法，无返回值
     */
    abstract fun onJsToBridge(msg: String?, params: MutableMap<String, Any>?, tag: Int?)

    /**
     * js调用Bridge方法，有返回值
     */
    abstract fun onJsToBridgeSync(msg: String?, params: MutableMap<String, Any>?, tag: Int?): Any

    /**
     * 返回JsToBridge hook js
     */
    abstract fun getInjectJs(): String
}