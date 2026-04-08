package yz.l.compose.impl.provider

import yz.l.compose.api.LoginApi
import yz.l.compose.api.LoginNavKey
import yz.l.compose.data.LoginParams
import yz.l.compose.data.LoginResult
import yz.l.compose.impl.states.AuthContext
import yz.l.core_router.Navigator
import yz.l.core_router.NavigatorRegister
import yz.l.core_router.NavigatorService

/**
 * desc:
 * created by liyuzheng on 2026/3/27 15:51
 */
class LoginProvider : LoginApi {
    init {
        NavigatorRegister.register(LoginNavKey::class.java.name, this)
    }

    override fun action(
        navigator: NavigatorService,
        requestLogin: Boolean,
        loginParams: MutableMap<LoginParams, Any>,
        interceptBlock: Boolean,
        block: (result: LoginResult) -> Unit
    ) {
        AuthContext.action(navigator, requestLogin, loginParams, interceptBlock, block)
    }
}