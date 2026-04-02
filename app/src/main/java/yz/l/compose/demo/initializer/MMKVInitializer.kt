package yz.l.compose.demo.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV

/**
 * desc:
 * created by liyuzheng on 2026/3/16 22:25
 */
class MMKVInitializer : Initializer<String> {
    override fun create(ctx: Context): String {
        Log.v("MMKVInitializer", "create MMKV")
        return MMKV.initialize(ctx)
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}