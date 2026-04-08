package yz.l.core_router.options

import androidx.navigation3.runtime.NavKey
import yz.l.core_router.strategy.NavigateModeStrategyContext

/**
 * desc:
 * created by liyuzheng on 2026/4/4 19:52
 */
data class NavigatorOptions(val navKey: NavKey?) {
    var navigateMode: Int = NavigateModeStrategyContext.DEFAULT
        private set

    fun navigateMode(value: Int) {
        navigateMode = value
    }

    var requestKey: String = ""
        private set

    fun requestKey(value: String) {
        requestKey = value
    }
}

inline fun navOpt(navKey: NavKey, init: NavigatorOptions.() -> Unit = {}): NavigatorOptions {
    val navOpts = NavigatorOptions(navKey)
    navOpts.init()
    return navOpts
}
