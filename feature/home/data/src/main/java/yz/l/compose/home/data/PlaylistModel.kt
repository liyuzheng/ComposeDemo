package yz.l.compose.home.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * desc:
 * created by liyuzheng on 2026/4/27 19:02
 */

@Serializable
class PlaylistModel(
    @SerialName("id")
    val playlistId: Int = 0,
    val name: String = "",
    @SerialName("creation_date")
    val creationDate: String = "",
    @SerialName("user_id")
    val userId: Int = 0,
    @SerialName("user_name")
    val userName: String = "",
    val zip: String = ""
)