package yz.l.compose.api

import yz.l.compose.data.LoginParams
import yz.l.compose.data.LoginResult
import yz.l.core_router.INavigationApi
import yz.l.core_router.Navigator
import yz.l.core_router.NavigatorService

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:34
 */
interface LoginApi : INavigationApi {
    fun action(
        navigator: NavigatorService,
        requestLogin: Boolean = true,
        loginParams: MutableMap<LoginParams, Any> = mutableMapOf(),
        interceptBlock: Boolean = false,
        block: (result: LoginResult) -> Unit
    )
}