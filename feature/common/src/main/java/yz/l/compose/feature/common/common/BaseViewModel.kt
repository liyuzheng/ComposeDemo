package yz.l.compose.feature.common.common

import androidx.lifecycle.ViewModel

/**
 * desc:
 * created by liyuzheng on 2026/4/14 16:27
 */
abstract class BaseViewModel : ViewModel() {
    open fun dispatchIntent(intent: BaseIntent) {}
}

interface BaseIntent