package net.jbridge.runtime

/**
 * 类型转换工具
 * Created by zhongyongsheng on 2018/8/10.
 */
class TypeConvertUtil {

    companion object {

        @JvmStatic
        fun safeObjectToInt(value: Any?): Int {
            return when (value) {
                null -> 0
                is Int -> value.toInt()
                is Long -> safeLongToInt(value.toLong(), 0)
                else -> 0
            }
        }

        @JvmStatic
        private fun safeLongToInt(value: Long, defaultValue: Int): Int {
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                return defaultValue
            }
            return value.toInt()
        }

        @JvmStatic
        fun safeObjectToLong(value: Any?): Long {
            return when (value) {
                null -> 0
                is Int -> value.toLong()
                is Long -> value
                else -> 0
            }
        }

        @JvmStatic
        fun safeObjectToBoolean(value: Any?): Boolean {
            return when (value) {
                null -> false
                is Boolean -> value
                else -> false
            }
        }

        @JvmStatic
        fun safeObjectToString(value: Any?): String {
            return when (value) {
                null -> ""
                is String -> value
                else -> ""
            }
        }

        @JvmStatic
        fun safeObjectToFloat(value: Any?): Float {
            return when (value) {
                null -> 0.0f
                is Float -> value
                else -> 0.0f
            }
        }

        @JvmStatic
        fun safeObjectToDouble(value: Any?): Double {
            return when (value) {
                null -> 0.0
                is Double -> value
                else -> 0.0
            }
        }
    }
}