package yz.l.compose.demo.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import yz.l.compose.impl.states.AuthContext

/**
 * desc:
 * created by liyuzheng on 2026/3/27 17:11
 */
class AuthContextInitializer : Initializer<Unit> {
    override fun create(ctx: Context) {
        Log.v("AuthContextInitializer", "create authContext")
        AuthContext.init()
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}