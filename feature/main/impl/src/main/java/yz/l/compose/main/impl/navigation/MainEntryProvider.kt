package yz.l.compose.main.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import yz.l.compose.main.api.MainNavKey
import yz.l.compose.main.impl.MainScreen

/**
 * desc:
 * created by liyuzheng on 2026/4/6 19:49
 */

fun EntryProviderScope<NavKey>.mainScreenEntry() {
    entry<MainNavKey> { _ ->
        MainScreen()
    }
}