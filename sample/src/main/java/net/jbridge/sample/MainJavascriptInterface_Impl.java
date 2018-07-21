package net.jbridge.sample;

import android.webkit.JavascriptInterface;

import net.jbridge.sample.CatBridgeInterface;
import net.jbridge.sample.MainJavascriptInterface;
import net.jbridge.util.CommonUtil;

import java.util.Map;

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
public class MainJavascriptInterface_Impl extends MainJavascriptInterface {

//    private Map<Class<?>, Object> mBridgeImpls;

//    public MainJavascriptInterface_Impl(Map<Class<?>, Object> map) {
//        mBridgeImpls = map;
//    }

    @JavascriptInterface
    public void cat(int index, String name, long times) {
//        CommonUtil.INSTANCE.getTypeInstance(mBridgeImpls, CatBridgeInterface.class).callCat(index, name, times);
    }
}
