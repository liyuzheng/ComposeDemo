package yz.l.core_router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * desc:
 * created by liyuzheng on 2026/3/22 16:43
 */

class ResultStore {

    /**
     * Map from the result key to a mutable state of the result.
     */
    val resultStateMap = mutableStateMapOf<String, MutableState<Any?>>()

    /**
     * Retrieves the current result of the given resultKey.
     */
    inline fun <reified T> getResultState(resultKey: String = T::class.toString()) =
        resultStateMap[resultKey]?.value as T?

    /**
     * Sets the result for the given resultKey.
     */
    inline fun <reified T> setResult(resultKey: String, result: T) {
        resultStateMap[resultKey] = mutableStateOf(result)
    }

    /**
     * Removes all results associated with the given key from the store.
     */
    inline fun <reified T> removeResult(resultKey: String = T::class.toString()) {
        resultStateMap.remove(resultKey)
    }
}

/** Saver to save and restore the NavController across config change and process death. */
private fun resultStoreSaver(): Saver<ResultStore, *> =
    Saver(
        save = { store ->
            // Save only the values, not the MutableState objects
            store.resultStateMap.mapValues { (_, state) -> state.value }
        },
        restore = { savedMap ->
            ResultStore().apply {
                // Restore the values as new MutableState objects
                savedMap.forEach { (key, value) ->
                    resultStateMap[key] = mutableStateOf(value)
                }
            }
        },
    )

@Composable
fun rememberResultStore(): ResultStore {
    return rememberSaveable(saver = resultStoreSaver()) {
        ResultStore()
    }
}
