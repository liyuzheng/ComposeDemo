package yz.l.compose.demo.initializer

import android.content.Context
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV
import timber.log.Timber

/**
 * desc:
 * created by liyuzheng on 2026/3/16 22:25
 */
class MMKVInitializer : Initializer<String> {
    override fun create(ctx: Context): String {
        Timber.v("create MMKVInitializer")
        return MMKV.initialize(ctx)
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return listOf(TimberInitializer::class.java)
    }
}