package yz.l.compose.discover.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import yz.l.compose.discover.api.DiscoverNavKey
import yz.l.compose.discover.impl.DiscoverScreen

/**
 * desc:
 * created by liyuzheng on 2026/4/7 13:55
 */
fun EntryProviderScope<NavKey>.discoverScreenEntry() {
    entry<DiscoverNavKey> { _ ->
        DiscoverScreen()
    }
}