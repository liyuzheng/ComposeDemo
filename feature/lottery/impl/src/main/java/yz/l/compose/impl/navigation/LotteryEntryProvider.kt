package yz.l.compose.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import yz.l.compose.api.LotteryNavKey
import yz.l.compose.impl.LotteryScreen
import yz.l.core_router.LocalNavigator
import yz.l.core_router.NavigatorService

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:52
 */
fun EntryProviderScope<NavKey>.lotteryScreenEntry(navigator: NavigatorService) {
    entry<LotteryNavKey>(metadata = mapOf("needLogin" to "needLogin")) { key ->
        LotteryScreen(key.id) {

        }
    }
}

