package yz.l.compose.impl

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import yz.l.compose.data.TokenModel
import yz.l.compose.feature.common.common.BaseViewModel
import yz.l.core_tool.BuildConfig
import yz.l.network.RequestMode
import yz.l.network.ext.repo
import yz.l.network.ext.request
import yz.l.network.onCancel
import yz.l.network.onCompletion
import yz.l.network.onFailure
import yz.l.network.onLoading
import yz.l.network.onSuccess
import yz.l.network.token.TokenManager
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/3/16 20:07
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(true) {

    fun requestToken(code: String) {
        repo {
            api("https://api.jamendo.com/v3.0/oauth/grant")
            requestMode(RequestMode.POST)
            params { "client_id" to BuildConfig.clientId }
            params { "client_secret" to BuildConfig.secret }
            params { "grant_type" to "authorization_code" }
            params { "code" to code }
            params { "redirect_uri" to "yuzheng://auth_success" }
            needAccessToken(false)
        }.request<TokenModel>(viewModelScope) { result ->
            result.onLoading {
                Timber.v("onLoading")
            }
            result.onSuccess {
                TokenManager.save(it.accessToken, it.refreshToken, it.expiresIn)
            }
            result.onFailure {
                Timber.v("requestToken err $it")
            }
            result.onCancel {
                Timber.v("requestToken onCancel")
            }
            result.onCompletion {
                Timber.v("requestToken onCompletion")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}