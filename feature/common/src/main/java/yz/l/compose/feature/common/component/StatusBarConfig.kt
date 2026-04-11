package yz.l.compose.feature.common.component

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * desc:
 * created by liyuzheng on 2026/4/5 23:04
 */
@Composable
fun ShowStatusBar(show: Boolean) {
    val view = LocalView.current
    val window = LocalActivity.current?.window
    SideEffect {
        val controller = WindowCompat.getInsetsController(window ?: return@SideEffect, view)
        if (show)
            controller.show(WindowInsetsCompat.Type.statusBars())
        else
            controller.hide(WindowInsetsCompat.Type.statusBars())
        controller.isAppearanceLightStatusBars = true
    }
}