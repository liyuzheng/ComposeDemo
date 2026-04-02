package yz.l.compose.impl.states.exts

import android.util.Log
import yz.l.compose.api.navigateToLogin
import yz.l.compose.data.LoginParams
import yz.l.compose.data.LoginResult
import yz.l.compose.impl.states.AuthContext
import yz.l.core_router.Navigator

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:39
 */

fun Navigator.requestLogin(
    loginParams: MutableMap<LoginParams, Any> = mutableMapOf(),
    block: (LoginResult) -> Unit
) {
    val currentUserId = AuthContext.loginStateFlow.value?.uid
    Log.v("currentUserId", "id:${currentUserId.isNullOrBlank()}")
    if (currentUserId.isNullOrBlank()) {
        navigateToLogin("login", block)
    } else {
        block(LoginResult.Success())
    }
}

//when (this@requestLogin) {
//    is FragmentActivity -> {
//        LoginProxyFragment.getLoginProxyFragment(this@requestLogin, block)
//            .requestLogin(loginParams)
//    }
//
//    is Fragment -> {
//        LoginProxyFragment.getLoginProxyFragment(this@requestLogin.requireActivity(), block)
//            .requestLogin(loginParams)
//    }
//
//    else -> {
//        throw RuntimeException("requestLogin LifecycleOwner必须是activity或fragment")
//    }
//}
//}.onFailure {
//    block(LoginResult.Cancel())
//}