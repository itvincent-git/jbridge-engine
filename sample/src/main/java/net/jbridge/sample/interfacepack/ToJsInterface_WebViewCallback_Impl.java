package net.jbridge.sample.interfacepack;

import android.view.View;

import net.jbridge.sample.callback.WebViewCallback;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zhongyongsheng on 2018/7/23.
 */
public class ToJsInterface_WebViewCallback_Impl extends WebViewCallback implements ToJsInterface {


    public ToJsInterface_WebViewCallback_Impl(@NotNull View view) {
        super(view);
    }

    @Override
    public void onCallback(@NotNull String value) {
        eval("javascript:onCallback(\"" + value + "\")");
    }
}
