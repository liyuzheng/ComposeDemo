package yz.l.compose.impl.states

import yz.l.compose.data.LoginParams
import yz.l.compose.data.LoginResult
import yz.l.core_router.NavigatorService

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:10
 */
class AuthState : IAuthState {
    override fun action(
        navigator: NavigatorService,
        requestLogin: Boolean,
        loginParams: MutableMap<LoginParams, Any>,
        block: (result: LoginResult) -> Unit
    ) {
        block(LoginResult.Success())
    }

}