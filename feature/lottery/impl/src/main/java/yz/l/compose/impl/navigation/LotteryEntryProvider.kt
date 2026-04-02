package yz.l.compose.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import yz.l.compose.api.LoginNavKey
import yz.l.compose.api.LotteryNavKey
import yz.l.compose.impl.LotteryScreen
import yz.l.core_router.LocalNavigator

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:52
 */
fun EntryProviderScope<NavKey>.lotteryScreenEntry() {
    entry<LotteryNavKey> { key ->
        val navigator = LocalNavigator.current
        LotteryScreen(key.id) {
            navigator.navigate(LoginNavKey())
        }
    }
}

