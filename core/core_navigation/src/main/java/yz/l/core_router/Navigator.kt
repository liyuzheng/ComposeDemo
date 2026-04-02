package yz.l.core_router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * desc:
 * created by liyuzheng on 2026/3/15 22:37
 */
class Navigator(
    val backStack: NavBackStack<NavKey>,
    val resultStore: ResultStore
) {

    fun navigate(navKey: NavKey) {
        backStack.add(navKey)
    }

    fun navigateAndRemoveCurrent(navKey: NavKey) {
        val current = backStack.lastOrNull()
        backStack.add(navKey)
        backStack.remove(current)
    }

    fun getCurrentNavKey(): NavKey? {
        return backStack.lastOrNull()
    }

    fun back() {
        backStack.removeLastOrNull()
    }

    inline fun <reified T> setResult(resultKey: String, result: T) {
        resultStore.setResult(resultKey, result)
    }

    fun popWithResult(resultKey: String, result: String) {
        resultStore.setResult<String>(resultKey = resultKey, result = result)
        backStack.removeLastOrNull()
    }

    @Composable
    inline fun <reified T> getResultState(resultKey: String): T? {
        DisposableEffect(resultKey, resultStore.resultStateMap[resultKey]) {
            onDispose {
                resultStore.removeResult<T>(resultKey)
            }
        }
        return resultStore.getResultState<T>(resultKey)
    }

    fun navigateBackTo() {

    }

    inline fun <reified T> removeResultState(resultKey: String) {
        resultStore.removeResult<T>(resultKey)
    }

}

val LocalNavigator = compositionLocalOf<Navigator> {
    error("No LocalNavigator given")
}
