package net.jbridge.sample;

import android.webkit.JavascriptInterface;

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
public class MainJavascriptInterface_Impl extends MainJavascriptInterface {

    @JavascriptInterface
    public void callCat(int index, String name, long times) {
        getCatBridge().callCat(index, name, times);
    }

    @JavascriptInterface
    public void callDog(int index, String name, long times) {
        getDogBridge().callDog(index, name, times);
    }
}
