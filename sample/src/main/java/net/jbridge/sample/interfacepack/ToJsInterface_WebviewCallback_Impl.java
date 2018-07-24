package net.jbridge.sample.interfacepack;

import android.view.View;
import android.webkit.WebView;

import net.jbridge.sample.callback.WebviewCallback;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zhongyongsheng on 2018/7/23.
 */
public class ToJsInterface_WebviewCallback_Impl extends WebviewCallback implements ToJsInterface {


    public ToJsInterface_WebviewCallback_Impl(@NotNull View view) {
        super(view);
    }

    @Override
    public void onCallback(@NotNull String value) {
        eval("javascript:onCallback(\"" + value + "\")");
    }
}
