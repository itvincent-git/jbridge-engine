package net.jbridge

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
class JBridgeBuilder<T: BaseJBridge>(val mTransformerClass: Class<T>) {
//    private val mBridgeImpls = HashMap<Class<*>, Any>()
//
//    fun <T> addImpl(interfaceClass: Class<T>, impl: T): JBridgeBuilder {
//        mBridgeImpls[interfaceClass] = impl as Any
//        return this
//    }
//
//    fun <T> getImpl(interfaceClass: Class<T>): T {
//        return mBridgeImpls[interfaceClass] as T
//    }
//
//    fun buildJavascriptInterface(): Any {
//        val javascriptInterface = JBridgeJavascriptInterface_Impl()
//
//        val implementation = getGeneratedImplementation<T, T>(mTransformerClass, IMPL_SUFFIX)
//    }

    fun build(): T {
        val implementation = getGeneratedImplementation<T>(mTransformerClass, IMPL_SUFFIX)
        //implementation.init(mSender)
        return implementation
    }

    companion object {
        private val IMPL_SUFFIX = "_Impl"

        fun <T : BaseJBridge> newBuilder(
                cls: Class<T>): JBridgeBuilder<T> {
            return JBridgeBuilder(cls)
        }

        internal fun <T> getGeneratedImplementation(cls: Class<T>, suffix: String): T {
            val fullPackage = cls.getPackage().name
            val name = cls.canonicalName
            val postPackageName = if (fullPackage.isEmpty())
                name
            else
                name.substring(fullPackage.length + 1)
            val implName = postPackageName.replace('.', '_') + IMPL_SUFFIX
            try {

                val aClass = Class.forName(
                        if (fullPackage.isEmpty()) implName else "$fullPackage.$implName") as Class<T>
                return aClass.newInstance()
            } catch (e: ClassNotFoundException) {
                throw RuntimeException("cannot find implementation for "
                        + cls.canonicalName + ". " + implName + " does not exist")
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot access the constructor" + cls.canonicalName)
            } catch (e: InstantiationException) {
                e.printStackTrace()
                throw RuntimeException("Failed to create an instance of " + cls.canonicalName)
            }

        }
    }

}