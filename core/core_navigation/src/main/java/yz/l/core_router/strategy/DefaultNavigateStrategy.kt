package yz.l.core_router.strategy

import android.util.Log
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import yz.l.core_router.options.NavigatorOptions

/**
 * desc:
 * created by liyuzheng on 2026/4/4 19:10
 */
class DefaultNavigateStrategy : AbsSimpleNavigateStrategy() {
    override fun navigate(backStack: NavBackStack<NavKey>, navigatorOptions: NavigatorOptions) {
        Log.v("DefaultNavigateStrategy", "navigate")
        backStack.add(navigatorOptions.navKey?:return)
    }
}