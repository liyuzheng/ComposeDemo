package yz.l.compose.impl.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import yz.l.compose.data.LoginParams
import yz.l.compose.data.LoginResult
import yz.l.compose.data.UserMMKV
import yz.l.compose.data.UserModel
import yz.l.core_router.NavigatorService

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:10
 */
object AuthContext {
    private var mLoginState: IAuthState = GuestState()
    val loginStateFlow = MutableStateFlow<UserModel?>(null)

    fun init() {
//        if (UserMMKV.currentUser.uid.isNotBlank() &&
//            UserMMKV.token.isNotBlank()
//        ) {
//            login(UserMMKV.currentUser, UserMMKV.token)
//        } else {
//            logout()
//        }
    }

    fun logout() {
        mLoginState = GuestState()
        UserMMKV.currentUser = UserModel()
        UserMMKV.token = ""
        loginStateFlow.tryEmit(null)
    }

    fun login(userModel: UserModel, token: String) {
        UserMMKV.currentUser = userModel
        UserMMKV.token = token
        mLoginState = AuthState()
        loginStateFlow.tryEmit(userModel)
    }

    fun action(
        navigator: NavigatorService,
        requestLogin: Boolean = true,
        loginParams: MutableMap<LoginParams, Any> = mutableMapOf(),
        interceptBlock: Boolean = false,
        block: (result: LoginResult) -> Unit
    ) {
        val afterLoginAction = if (interceptBlock) EMPTY_BLOCK else block
        mLoginState.action(navigator, requestLogin, loginParams, afterLoginAction)
    }

    private val EMPTY_BLOCK = { _: LoginResult -> }

    @Composable
    fun LoginStateChange(block: @Composable (UserModel?) -> Unit) {
        val loginState = LocalLoginState.current
        block(loginState)
    }
}

val LocalLoginState = staticCompositionLocalOf<UserModel?> {
    error("LocalLoginState should be initialized at runtime")
}


