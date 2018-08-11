package net.jbridge.sample

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import net.jbridge.sample.callback.WebViewCallback

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        web_wv.settings.javaScriptEnabled = true
        web_wv.settings.builtInZoomControls = false
        web_wv.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    view?.loadUrl(url)
                }
                return true
            }
        }
        //****define JavascriptInterface***
        val mainJBridge = MainJBridge.newInstance(this, null, web_wv, WebViewCallback(web_wv))
        web_wv.addJavascriptInterface(mainJBridge, "nativeApp")
        //*******
        web_wv.loadUrl("file:///android_asset/index.html")
        web_wv.loadUrl("javascript:" + mainJBridge.getInjectJs())

        onJsToBridge_btn.setOnClickListener {
            mainJBridge.test_onJsToBridge()
        }
    }
}
