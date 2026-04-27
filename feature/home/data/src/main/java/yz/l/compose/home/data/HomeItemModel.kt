package yz.l.compose.home.data

import kotlinx.serialization.Serializable

/**
 * desc:
 * created by liyuzheng on 2026/4/27 19:10
 */
@Serializable
data class HomeItemModel(
    val feedId: Long,
    val type: String,
    val position: Int = 0,
    val tracks: List<TrackModel> = emptyList(),
    val radios: List<RadioModel> = emptyList(),
    val playlist: PlaylistModel? = null
)