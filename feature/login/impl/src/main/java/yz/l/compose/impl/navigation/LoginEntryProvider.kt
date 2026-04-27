package yz.l.compose.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import yz.l.compose.api.LoginNavKey
import yz.l.compose.impl.LoginScreen
import yz.l.core_router.NavigatorService

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:52
 */
fun EntryProviderScope<NavKey>.loginScreenEntry(navigator: NavigatorService) {
    entry<LoginNavKey>(
        metadata = mapOf<String, Any>("test" to "1234")
    ) { navKey ->
        LoginScreen()
    }
}