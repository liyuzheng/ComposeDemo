package yz.l.compose.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yz.l.compose.data.LoginResult
import yz.l.core_router.Navigator

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:00
 */

@Serializable
data class LoginNavKey(val requestKey: String = "", val block: (LoginResult) -> Unit = {}) : NavKey

fun Navigator.navigateToLogin(
    requestKey: String,
    block: (LoginResult) -> Unit
) {
    navigate(LoginNavKey(requestKey, block))
}
