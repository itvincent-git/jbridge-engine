package net.jbridge.sample;

import android.webkit.JavascriptInterface;

import net.jbridge.JBridgeContext;
import net.jbridge.sample.interfacepack.ToJsInterface;
import net.jbridge.sample.interfacepack.ToJsInterface_WebViewCallback_Impl;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
public class MainJBridge_Impl extends MainJBridge {
    private volatile ToJsInterface _toJsInterface;

    @JavascriptInterface
    public void callCat(int index, String name, long times) {
        getCatBridge().callCat((JBridgeContext<MainJBridge>) bridgeContext, index, name, times);
    }

    @JavascriptInterface
    public void callDog(int index, String name, long times) {
        getDogBridge().callDog(index, name, times);
    }

    @NotNull
    @Override
    public ToJsInterface getToJsInterface() {
        if (_toJsInterface != null) {
            return _toJsInterface;
        } else {
            synchronized(this) {
                if (_toJsInterface == null) {
                    _toJsInterface = new ToJsInterface_WebViewCallback_Impl(bridgeContext.getViewRef().get());
                }
                return _toJsInterface;
            }
        }
    }
}
