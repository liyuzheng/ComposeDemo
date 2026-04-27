package yz.l.compose.feature.common.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import yz.l.compose.feature.common.events.Bus
import yz.l.compose.feature.common.events.Event

/**
 * desc:
 * created by liyuzheng on 2026/4/14 16:27
 */
abstract class BaseViewModel(needEvent: Boolean = false) : ViewModel() {
    init {
        if (needEvent) {
            // viewModelScope 随 ViewModel 销毁而取消，页面进入后台不会被取消
            viewModelScope.launch {
                Bus.flowBus.collect { event ->
                    // 无论页面在前台还是后台，都会执行
                    _eventFlow.emit(event)
                }
            }
        }
    }

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow

    open fun dispatchIntent(intent: BaseIntent) {}
}

interface BaseIntent