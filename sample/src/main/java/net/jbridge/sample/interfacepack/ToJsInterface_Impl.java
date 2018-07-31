package net.jbridge.sample.interfacepack;

import net.jbridge.common.JBridgeCallback;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zhongyongsheng on 2018/7/23.
 */
public class ToJsInterface_Impl implements ToJsInterface {
    private JBridgeCallback _callback;

    public ToJsInterface_Impl(@NotNull JBridgeCallback callback) {
        _callback = callback;
    }

    @Override
    public void onCallback(@NotNull String value) {
        _callback.eval("javascript:onCallback(\"" + value + "\")");
    }
}
