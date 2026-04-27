package yz.l.compose.feature.common.events

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * desc:
 * created by liyuzheng on 2026/4/24 19:31
 */
object Bus {
    private val mScope by lazy { CoroutineScope(Dispatchers.IO) }
    private val _flowBus = MutableSharedFlow<Event>()
    val flowBus: SharedFlow<Event> = _flowBus
    fun emit(event: Event) {
        mScope.launch {
            val result = _flowBus.emit(event)
            Timber.v("emit $result $event")
        }
    }

    inline fun <reified T : Event> onEach(scope: CoroutineScope, crossinline block: (T) -> Unit) {
        flowBus.filter {
            it is T
        }.onEach {
            block(it as T)
        }.launchIn(scope).start()
    }

    @Suppress("UNCHECKED_CAST")
    @Composable
    fun <T : Event> collectAsStateWithLifecycle() =
        flowBus.collectAsStateWithLifecycle(null).value as? T
}