package net.jbridge.sample.callback

import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import net.jbridge.common.JBridgeCallback

/**
 * Created by zhongyongsheng on 2018/7/21.
 */
open class WebviewCallback(view: View) : JBridgeCallback {
    val handler = Handler(Looper.getMainLooper())
    var webView: WebView? = null

    init {
        if (view is WebView)
            webView = view
    }

    override fun eval(eval: String) {
        handler.post {
            webView?.loadUrl(eval)
        }
    }
}