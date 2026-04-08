package yz.l.compose.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import timber.log.Timber
import yz.l.compose.discover.impl.navigation.discoverScreenEntry
import yz.l.compose.home.api.HomeNavKey
import yz.l.compose.home.impl.navigation.homeScreenEntry
import yz.l.compose.impl.navigation.loginGuideScreenEntry
import yz.l.compose.impl.navigation.loginScreenEntry
import yz.l.compose.impl.navigation.lotteryScreenEntry
import yz.l.core_router.LocalNavigator
import yz.l.core_router.NavigatorService
import yz.l.core_router.rememberServiceSaver

/**
 * desc:
 * created by liyuzheng on 2026/3/22 17:19
 */

@Composable
fun AppHost() {
    CompositionLocalProvider(
        LocalNavigator provides rememberNavigator(),
    ) {
        val navigator = LocalNavigator.current
        NavDisplay(
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            backStack = navigator.backStack,
            onBack = { navigator.back() },
            entryProvider = entryProvider {
                loginScreenEntry(navigator)
                lotteryScreenEntry(navigator)
                loginGuideScreenEntry(navigator)
                homeScreenEntry()
                discoverScreenEntry()
            })
    }
}

@Composable
fun rememberNavigator(): NavigatorService {
    val backStack = rememberNavBackStack(HomeNavKey)
    backStack.forEach {
        Timber.v("backStack $it")
    }
    return rememberServiceSaver(backStack)
}

@Composable
@Preview
fun LotteryScreenPreview() {

}
