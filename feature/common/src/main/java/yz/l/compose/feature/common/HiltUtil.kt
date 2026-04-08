package yz.l.compose.feature.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.EntryPointAccessors

/**
 * desc:
 * created by liyuzheng on 2026/4/7 11:23
 */
@Composable
fun <T> injectModule(entryPoint: Class<T>): T {
    val context = LocalContext.current
    return remember(context) {
        EntryPointAccessors.fromApplication(
            context.applicationContext,
            entryPoint
        )
    }
}