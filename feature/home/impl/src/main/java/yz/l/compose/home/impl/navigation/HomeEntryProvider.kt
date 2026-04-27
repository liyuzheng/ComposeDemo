package yz.l.compose.home.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import yz.l.compose.home.api.HomeNavKey
import yz.l.compose.home.impl.HomeScreen

/**
 * desc:
 * created by liyuzheng on 2026/4/25 16:38
 */
fun EntryProviderScope<NavKey>.homeScreenEntry() {
    entry<HomeNavKey> { _ ->
        HomeScreen()
    }
}