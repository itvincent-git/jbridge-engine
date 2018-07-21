package net.jbridge

import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import net.jbridge.common.JBridgeCallback

/**
 * Created by zhongyongsheng on 2018/7/21.
 */
class WebviewCallback(val webview: WebView) : JBridgeCallback {
    val handler = Handler(Looper.getMainLooper())

    override fun eval(eval: String) {
        handler.post {
            webview.loadUrl(eval)
        }
    }
}