package net.jbridge.sample.callback

import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import net.jbridge.common.JBridgeCallback

/**
 * Created by zhongyongsheng on 2018/7/21.
 */
open class GameEngineCallback(val gameEngine: IGameEngine) : JBridgeCallback {
    val handler = Handler(Looper.getMainLooper())

    override fun eval(eval: String) {
        handler.post {
            gameEngine?.eval(eval)
        }
    }

    interface IGameEngine {
        fun eval(value: String)
    }
}