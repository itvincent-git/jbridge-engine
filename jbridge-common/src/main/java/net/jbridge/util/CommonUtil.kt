package net.jbridge.util

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
object CommonUtil {

    fun <T> getTypeInstance(map: Map<Class<*>, Any>, clazz: Class<T>): T {
        return map[clazz] as T
    }
}