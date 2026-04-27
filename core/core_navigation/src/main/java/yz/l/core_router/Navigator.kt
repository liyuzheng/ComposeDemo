package yz.l.core_router

import android.util.Log
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * desc:
 * created by liyuzheng on 2026/3/15 22:37
 */
class Navigator(
    val backStack: NavBackStack<NavKey>,
    val resultStore: ResultStore
) {
    fun navigate(navKey: NavKey) {
        Log.v("result", "navigate1111 $navKey")
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
        Log.v("resul111t", "navigate1111 ${backStack.lastOrNull()}")
        backStack.removeLastOrNull()
    }

    inline fun <reified T> setResult(resultKey: String, result: T) {
        resultStore.setResult(resultKey, result)
    }

    fun popWithResult(resultKey: String, result: String) {
        resultStore.setResult<String>(resultKey = resultKey, result = result)
        back()
    }

    //    @Composable
//    inline fun <reified T> getResultState(resultKey: String): T? {
//        DisposableEffect(resultKey, resultStore.resultStateMap[resultKey]) {
//            onDispose {
//                resultStore.removeResult<T>(resultKey)
//            }
//        }
//        return resultStore.getResultState<T>(resultKey)
//    }
    val resultCallbacks = mutableMapOf<String, (Any?) -> Unit>()
    suspend inline fun <reified T> navigateForResult(
        navKey: NavKey,
        resultKey: String
    ): T? {
        val currNavKey = getCurrentNavKey().toString()
        Log.v("result", "curr $currNavKey")
        return suspendCancellableCoroutine { continuation ->
            Log.v("result", "resultCallbacks1111")
            resultCallbacks[resultKey] = { result ->
                Log.v("result", "resultCallbacks $result")
                if (result != null) {
                    continuation.resumeWith(Result.success(result as T))
                } else {
                    continuation.resumeWith(Result.success(null))
                }
            }
            navigate(navKey)
            continuation.invokeOnCancellation { th ->
                Log.v("result", "resultCallbacks remove $th")
//                continuation.resumeWith(Result.failure(Throwable()))
//                resultCallbacks.remove(resultKey)
            }
        }
    }


    fun navigateBackTo() {

    }

    inline fun <reified T> removeResultState(resultKey: String) {
        resultStore.removeResult<T>(resultKey)
    }

}