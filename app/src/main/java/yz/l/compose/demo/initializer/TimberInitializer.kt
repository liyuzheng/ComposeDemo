package yz.l.compose.demo.initializer

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber
import yz.l.network.BuildConfig

/**
 * desc:
 * created by liyuzheng on 2026/4/6 20:53
 */
class TimberInitializer : Initializer<Unit> {
    override fun create(ctx: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return "${super.createStackElementTag(element)}.${element.methodName}:${element.lineNumber}"
                }

                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, tag, "$tag &&|| $message", t)
                }
            })
            Timber.v("create TimberInitializer")
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}