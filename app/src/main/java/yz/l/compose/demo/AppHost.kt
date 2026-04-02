package yz.l.compose.demo

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import yz.l.compose.api.LoginGuideNavKey
import yz.l.compose.demo.themes.AppTheme
import yz.l.compose.impl.LotteryScreen
import yz.l.compose.impl.navigation.loginGuideScreenEntry
import yz.l.compose.impl.navigation.loginScreenEntry
import yz.l.compose.impl.navigation.lotteryScreenEntry
import yz.l.compose.impl.states.AuthContext
import yz.l.compose.impl.states.LocalLoginState
import yz.l.core_router.LocalNavigator
import yz.l.core_router.Navigator
import yz.l.core_router.rememberResultStore

/**
 * desc:
 * created by liyuzheng on 2026/3/22 17:19
 */

@Composable
fun AppHost() {
    Log.v("Initializer", "Apphost")
    val navigator = rememberNavigator()
    navigator.navigate(LoginGuideNavKey)
    CompositionLocalProvider(
        LocalNavigator provides navigator,
        LocalLoginState provides AuthContext.loginStateFlow.collectAsStateWithLifecycle().value
    ) {
        NavDisplay(
            entryDecorators = listOf(
                // Add the default decorators for managing scenes and saving state
                rememberSaveableStateHolderNavEntryDecorator(),
                // Then add the view model store decorator
                rememberViewModelStoreNavEntryDecorator()
            ),
            backStack = navigator.backStack,
            onBack = { navigator.back() },
            entryProvider = entryProvider {
                loginScreenEntry()
                lotteryScreenEntry()
                loginGuideScreenEntry()
            })
    }
}

@Composable
fun rememberNavigator(): Navigator {
    val backStack = rememberNavBackStack()
    val resultStore = rememberResultStore()
    return Navigator(backStack, resultStore)
}

@Composable
@Preview
fun LotteryScreenPreview() {
    AppTheme(true) {
        LotteryScreen("") { }
    }
}
