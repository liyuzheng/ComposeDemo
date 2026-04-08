package yz.l.compose.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import yz.l.compose.feature.common.room.dao.DiscoverCardDao
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/3/14 18:29
 */
@HiltViewModel
class MainActViewModel @Inject constructor(
    private val topicDao: DiscoverCardDao,
) : ViewModel() {
    val isReadyObs = MutableStateFlow(false)

    fun init() {
        viewModelScope.launch {
            isReadyObs.value = true
        }
    }
}