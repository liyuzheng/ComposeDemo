package yz.l.compose.impl.states

import yz.l.compose.data.LoginParams
import yz.l.compose.data.LoginResult
import yz.l.core_router.Navigator
import yz.l.core_router.NavigatorService

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:10
 */
interface IAuthState {
    /**
     * @param context
     * @param requestLogin 当游客状态时，执行某个动作是否需要呼起登录弹框
     * @param loginParams 登录参数，传入loginActivity
     * @param block 执行结果回调
     */
    fun action(
        navigator: NavigatorService,
        requestLogin: Boolean,
        loginParams: MutableMap<LoginParams, Any>,
        block: (result: LoginResult) -> Unit
    )
}