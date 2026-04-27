package yz.l.compose.main.impl

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import yz.l.compose.feature.common.common.BaseIntent
import yz.l.compose.feature.common.common.BaseViewModel
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/14 16:07
 */
@HiltViewModel
class MainScreenViewModel @Inject constructor() : BaseViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    override fun dispatchIntent(intent: BaseIntent) {
        when (intent) {
            is HomeIntent.SelectedIndexChangedIntent -> {
                _uiState.value = _uiState.value.copy(selectedIndex = intent.index)
            }
        }
    }
}

data class HomeUiState(
    val selectedIndex: Int = 0
)

data class HomeState(val selectedIndex: Int)
sealed class HomeIntent : BaseIntent {
    data class SelectedIndexChangedIntent(val index: Int) : HomeIntent()
}