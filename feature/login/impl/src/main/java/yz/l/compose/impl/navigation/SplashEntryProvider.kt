package yz.l.compose.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import yz.l.compose.api.LoginGuideNavKey
import yz.l.compose.impl.LoginGuideScreen
import yz.l.core_router.NavigatorService

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:52
 */
fun EntryProviderScope<NavKey>.loginGuideScreenEntry(navigatorService: NavigatorService) {
    entry<LoginGuideNavKey> {
        LoginGuideScreen(navigatorService)
    }
}

