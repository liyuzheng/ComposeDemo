package yz.l.compose.api

import yz.l.compose.data.LoginParams
import yz.l.compose.data.LoginResult
import yz.l.core_router.Navigator
import yz.l.core_router.NavigatorRegister
import yz.l.core_router.NavigatorService

/**
 * desc:
 * created by liyuzheng on 2026/3/27 16:05
 */
object LoginProxy {
    private val provider by lazy {
        NavigatorRegister.getProvider(LoginNavKey::class.java.name) as LoginApi
    }

    fun action(
        navigator: NavigatorService,
        requestLogin: Boolean = true,
        loginParams: MutableMap<LoginParams, Any> = mutableMapOf(),
        interceptBlock: Boolean = false,
        block: (result: LoginResult) -> Unit
    ) {
        provider.action(navigator, requestLogin, loginParams, interceptBlock, block)
    }
}