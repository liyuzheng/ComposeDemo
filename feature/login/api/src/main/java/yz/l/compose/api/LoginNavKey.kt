package yz.l.compose.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yz.l.compose.data.LoginResult
import yz.l.core_router.NavigatorService
import yz.l.core_router.options.navOpt

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:00
 */

@Serializable
data class LoginNavKey(
    val requestKey: String = "",
    val result: LoginResult = LoginResult.Success()
) : NavKey {

}

suspend fun NavigatorService.navigateToLogin(
    requestKey: String,
    block:suspend (LoginResult) -> Unit
) {

    navigate(navOpt(LoginNavKey()) {
        requestKey(requestKey)
    })
//    navigate(LoginNavKey(requestKey))
}
