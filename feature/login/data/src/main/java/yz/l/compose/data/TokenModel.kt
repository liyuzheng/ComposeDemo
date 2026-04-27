package yz.l.compose.data

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import yz.l.network.BaseResponse

/**
 * desc:
 * created by liyuzheng on 2026/4/25 15:18
 */
@Keep
@Serializable
data class TokenModel(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("refresh_token")
    val refreshToken: String
) : BaseResponse()