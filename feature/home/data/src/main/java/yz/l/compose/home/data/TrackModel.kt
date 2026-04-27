package yz.l.compose.home.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * desc:
 * created by liyuzheng on 2026/4/27 18:57
 */
@Serializable
data class TrackModel(
    @SerialName("id")
    val trackId: Int,
    val name: String,
    val duration: Int = 0,
    @SerialName("artist_id")
    val artistId: Int = 0,
    @SerialName("artist_name")
    val artistName: String = "",
    @SerialName("album_name")
    val albumName: String = "",
    @SerialName("album_id")
    val albumId: Int = 0,
    val position: Int = 0,
    val releasedData: String = "",
    @SerialName("album_image")
    val albumImage: String = "",
    val audio: String = "",
    val image: String = ""
)