package yz.l.compose.impl.navigation

import android.util.Log
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import yz.l.compose.api.LoginNavKey
import yz.l.compose.data.UserModel
import yz.l.compose.impl.LoginScreen
import yz.l.compose.impl.states.AuthContext
import yz.l.core_router.NavigatorService

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:52
 */
fun EntryProviderScope<NavKey>.loginScreenEntry(navigator: NavigatorService) {
    entry<LoginNavKey>(
        metadata = mapOf<String, Any>("test" to "1234")
    ) { navKey ->
        LoginScreen({
            Log.v("loginresult", "11111")
            AuthContext.login(UserModel("111111"), "1111111")
            navigator.backWithResult("testRequest", "testresult")
        })
    }
}