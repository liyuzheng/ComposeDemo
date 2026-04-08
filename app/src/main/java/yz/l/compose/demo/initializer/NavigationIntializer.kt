package yz.l.compose.demo.initializer

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber
import yz.l.compose.impl.provider.LoginProvider

/**
 * desc:
 * created by liyuzheng on 2026/3/27 17:11
 */
class NavigationInitializer : Initializer<Unit> {
    override fun create(ctx: Context) {
        LoginProvider()
        Timber.v("create NavigationInitializer")
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return listOf(TimberInitializer::class.java)
    }
}