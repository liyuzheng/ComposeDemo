package yz.l.core_router.strategy

import android.util.Log
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import yz.l.core_router.options.NavigatorOptions

/**
 * desc:
 * created by liyuzheng on 2026/4/4 19:45
 */
class ReplaceNavigateStrategy : AbsSimpleNavigateStrategy() {
    override fun navigate(backStack: NavBackStack<NavKey>, navigatorOptions: NavigatorOptions) {
        if (backStack.isNotEmpty()) {
            backStack[backStack.size - 1] = navigatorOptions.navKey ?: return
        } else {
            backStack.add(navigatorOptions.navKey ?: return)
        }
        Log.v(
            "ReplaceNavigateStrategy",
            "navigate to ${navigatorOptions.navKey} ${backStack.lastOrNull()}"
        )
    }
}