package yz.l.compose.demo.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import yz.l.compose.impl.provider.LoginProvider

/**
 * desc:
 * created by liyuzheng on 2026/3/27 17:11
 */
class NavigationInitializer : Initializer<Unit> {
    override fun create(ctx: Context) {
        Log.v("NavigationInitializer", "create Navigation")
        LoginProvider()
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}