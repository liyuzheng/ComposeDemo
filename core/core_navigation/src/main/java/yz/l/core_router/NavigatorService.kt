package yz.l.core_router

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.serialization.Serializable
import yz.l.core_router.options.NavigatorOptions
import yz.l.core_router.options.navOpt
import yz.l.core_router.strategy.NavigateModeStrategyContext

/**
 * desc:
 * created by liyuzheng on 2026/4/4 16:16
 */
class NavigatorService(
    val backStack: NavBackStack<NavKey>
) {
//    val results = mutableStateMapOf<String, Any?>()

    var resultFlow = MutableSharedFlow<NavigatorResult>(replay = 5)
    fun replace(navKey: NavKey) {
        navigate(navOpt(navKey) {
            navigateMode(NavigateModeStrategyContext.REPLACE)
        })
    }

    fun navigate(navKey: NavKey) {
        Log.v("NavigatorService", "navigate $navKey")
        navigate(navOpt(navKey))
    }

    fun navigate(navigatorOptions: NavigatorOptions) {
        Log.v("NavigatorService", "navigate ${navigatorOptions.navigateMode}")
        val strategy = NavigateModeStrategyContext.createStrategy(navigatorOptions.navigateMode)
        strategy.navigate(backStack, navigatorOptions)
    }

    fun backWithResult(resultKey: String, data: Any?) {
//        results[resultKey] = data
        resultFlow.tryEmit(NavigatorResult(resultKey, data))
        backStack.removeLastOrNull()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getResult(requestKey: String): T? {
//        return results.remove(requestKey) as? T
        return null
    }

    fun back() {
        backStack.removeLastOrNull()
    }
}

//private fun navigatorServiceSaver(backStack: NavBackStack<NavKey>): Saver<NavigatorService, *> =
//    Saver(
//        save = { store ->
////            store.resultFlow
//        },
//        restore = { savedMap ->
//            NavigatorService(backStack).apply {
//                resultFlow = savedMap
//            }
//        },
//    )

@Composable
fun rememberServiceSaver(backStack: NavBackStack<NavKey>): NavigatorService {
    return remember {
        NavigatorService(backStack)
    }
}

val LocalNavigator = staticCompositionLocalOf<NavigatorService> {
    error("No LocalNavigator given")
}

fun NavigatorService.resultFlow(requestKey: String) = resultFlow.filter {
    Log.v("resultFlow", "key ${it.key} $requestKey")
    it.key == requestKey
}

@Composable
inline fun <reified T> NavigatorService.ResultEffect(
    resultKey: String,
    crossinline onResult: suspend (T) -> Unit
) {
    val result by resultFlow(requestKey = resultKey).collectAsStateWithLifecycle(null)
    LaunchedEffect(result) {
        if (result == null) return@LaunchedEffect
        (result?.data as? T)?.run {
            onResult(this@run)
        }
    }
}
//
//@Composable
//inline fun <reified T> NavigatorService.ResultEffect(
//    resultKey: String,
//    crossinline onResult: suspend (T) -> Unit
//) {
//    val result = remember { getResult<String>(resultKey) }
//    LaunchedEffect(resultKey, result) {
//        if (result.isNullOrBlank()) {
//            return@LaunchedEffect
//        }
//        onResult(result as T)
//    }
//}

@Serializable
data class NavigatorResult(val key: String, val data: Any?)