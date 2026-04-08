package yz.l.core_router.strategy

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import yz.l.core_router.options.NavigatorOptions

/**
 * desc:
 * created by liyuzheng on 2026/4/4 19:08
 */
interface NavigateModeStrategy {
    fun navigate(
        backStack: NavBackStack<NavKey>,
        navigatorOptions: NavigatorOptions
    )
}