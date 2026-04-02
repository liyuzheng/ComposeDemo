package yz.l.compose.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * desc:
 * created by liyuzheng on 2026/3/16 20:07
 */
class LoginViewModel : ViewModel() {
    override fun onCleared() {
        super.onCleared()
    }

    fun test() {
        viewModelScope.launch {
            repeat(10000) {
                if (!isActive) return@launch
                delay(500)
            }
        }
    }
}