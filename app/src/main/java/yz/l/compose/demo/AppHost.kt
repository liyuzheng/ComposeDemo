package yz.l.compose.demo

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.util.Consumer
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import timber.log.Timber
import yz.l.compose.discover.impl.navigation.discoverScreenEntry
import yz.l.compose.game.impl.navigation.gameScreenEntry
import yz.l.compose.home.impl.navigation.homeScreenEntry
import yz.l.compose.impl.navigation.loginGuideScreenEntry
import yz.l.compose.impl.navigation.loginScreenEntry
import yz.l.compose.impl.navigation.lotteryScreenEntry
import yz.l.compose.main.api.MainNavKey
import yz.l.compose.main.impl.navigation.mainScreenEntry
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
        val context = LocalContext.current
        DeepLinkHandler(context)
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
                mainScreenEntry()
                discoverScreenEntry()
                gameScreenEntry()
                homeScreenEntry()
            })
    }
}

@Composable
fun DeepLinkHandler(context: Context) {
    DisposableEffect(context) {
        val listener = Consumer<Intent> { intent ->
            DeeplinkHelper.dispatcher(intent)
        }

        // 注册对新 Intent 的监听 (适用于 singleTask 模式)
        val activity = context as? ComponentActivity
        activity?.addOnNewIntentListener(listener)

        // 处理 App 首次启动时的 Intent
        activity?.intent?.let { listener.accept(it) }

        onDispose { activity?.removeOnNewIntentListener(listener) }
    }
}

@Composable
fun rememberNavigator(): NavigatorService {
    val backStack = rememberNavBackStack(MainNavKey)
    backStack.forEach {
        Timber.v("backStack $it")
    }
    return rememberServiceSaver(backStack)
}

@Composable
@Preview
fun LotteryScreenPreview() {

}


