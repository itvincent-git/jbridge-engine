package net.jbridge.annotation

/**
 * 标注在JBridge类的属性上
 * Created by zhongyongsheng on 2018/7/20.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD)
annotation class JBridgeField {
}