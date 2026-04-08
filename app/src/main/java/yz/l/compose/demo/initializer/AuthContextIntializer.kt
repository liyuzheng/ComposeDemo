package yz.l.compose.demo.initializer

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber
import yz.l.compose.impl.states.AuthContext

/**
 * desc:
 * created by liyuzheng on 2026/3/27 17:11
 */
class AuthContextInitializer : Initializer<Unit> {
    override fun create(ctx: Context) {
        AuthContext.init()
        Timber.v("create MMKVInitializer")
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return listOf(MMKVInitializer::class.java, TimberInitializer::class.java)
    }
}