package yz.l.compose.home.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * desc:
 * created by liyuzheng on 2026/4/27 18:50
 */
@Serializable
data class RadioModel(
    @SerialName("id")
    val radioId: Int = 0,
    val name: String = "",
    @SerialName("dispname")
    val displayName: String = "",
    val type: String = "",
    val image: String = "",
    val stream: String = ""
)