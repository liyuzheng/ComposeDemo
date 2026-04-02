package yz.l.compose.impl.states

import yz.l.compose.data.LoginParams
import yz.l.compose.data.LoginResult
import yz.l.compose.impl.states.exts.requestLogin
import yz.l.core_router.Navigator

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:10
 */
class GuestState : IAuthState {
    override fun action(
        navigator: Navigator,
        requestLogin: Boolean,
        loginParams: MutableMap<LoginParams, Any>,
        block: (result: LoginResult) -> Unit
    ) {
        if (requestLogin) {
            navigator.requestLogin(block = block)
        } else {
            block(LoginResult.Guest())
        }
    }
}