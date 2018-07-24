package net.jbridge

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import net.jbridge.common.JBridgeCallback

/**
 * Created by zhongyongsheng on 2018/7/20.
 */
class JBridgeBuilder<T: BaseJBridge>(val mTransformerClass: Class<T>/*, val mCallback: JBridgeCallback*/) {
    var mActivity: Activity? = null
    var mSupportFragment: Fragment? = null
    var mView: View? = null

    fun activity(activity: Activity?): JBridgeBuilder<T> {
        mActivity = activity
        return this
    }

    fun supportFragment(fragment: Fragment?): JBridgeBuilder<T> {
        mSupportFragment = fragment
        return this
    }

    fun view(view: View?): JBridgeBuilder<T> {
        mView = view
        return this
    }

    fun build(): T {
        val implementation = getGeneratedImplementation<T>(mTransformerClass, IMPL_SUFFIX)
        implementation.init(mActivity, mSupportFragment, mView/*, mCallback*/)
        return implementation
    }

    companion object {
        private val IMPL_SUFFIX = "_Impl"

        fun <T : BaseJBridge> newBuilder(
                cls: Class<T>/*, callbackClass: Class<JBridgeCallback>*/): JBridgeBuilder<T> {
            return JBridgeBuilder(cls/*, callback*/)
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
                throw RuntimeException("Failed to create an instance of " + cls.canonicalName)
            }

        }
    }

}