package yz.l.compose.feature.common.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import yz.l.network.BaseResponse

/**
 * desc:
 * created by liyuzheng on 2026/4/27 19:55
 */
@Serializable
data class BaseDataResponse<T>(
    val headers: ResponseHeaders?,
    val results: T? = null
) : BaseResponse()

@Serializable
data class ResponseHeaders(
    val status: String = "",
    val code: Int = 0,
    @SerialName("error_message")
    val errorMessage: String,
    @SerialName("results_count")
    val resultsCount: String,
    val next: String = ""
)