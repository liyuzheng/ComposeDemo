package yz.l.compose.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * desc:
 * created by liyuzheng on 2026/3/14 18:29
 */
class MainActViewModel : ViewModel() {
    val isReadyObs = MutableStateFlow(false)

    fun init() {
        viewModelScope.launch {
            delay(100)
            isReadyObs.value = true
        }
    }
}