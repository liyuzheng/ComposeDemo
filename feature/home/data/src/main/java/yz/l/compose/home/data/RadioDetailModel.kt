package yz.l.compose.home.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * desc:
 * created by liyuzheng on 2026/5/12 0:09
 */
@Serializable
data class RadioDetailModel(
    val id: Int,
    val name: String,
    @SerialName("dispname")
    val displayName: String,
    val type: String,
    val image: String,
    val stream: String,
    @SerialName("playingnow")
    val playingNow: PlayingNow,
    @SerialName("callmeback")
    val callMeBack: String
)

@Serializable
data class PlayingNow(
    @SerialName("track_id")
    val trackId: Int,
    @SerialName("artist_id")
    val artistId: Int,
    @SerialName("album_id")
    val albumId: Int,
    @SerialName("album_name")
    val albumName: String,
    @SerialName("track_name")
    val trackName: String,
    @SerialName("track_image")
    val trackImage: String,
    @SerialName("artist_name")
    val artistName: String
)