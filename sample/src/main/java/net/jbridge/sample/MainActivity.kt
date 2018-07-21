package net.jbridge.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import net.jbridge.WebviewCallback

class MainActivity : AppCompatActivity() {

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
        web_wv.addJavascriptInterface(MainJBridge.newInstance(this, null, web_wv, WebviewCallback(web_wv)), "nativeApp")
        //*******
        web_wv.loadUrl("file:///android_asset/index.html")
    }
}
