package yz.l.compose.home.data.mapper

import yz.l.compose.feature.common.room.entity.PlaylistEntity
import yz.l.compose.home.data.PlaylistModel

/**
 * desc:
 * created by liyuzheng on 2026/4/27 19:03
 */
fun PlaylistModel.toPlaylistEntity(remoteName: String) = PlaylistEntity(
    remoteName = remoteName,
    playlistId = playlistId,
    name = name,
    creationDate = creationDate,
    userId = userId,
    userName = userName,
    zip = zip
)

fun PlaylistEntity.toPlaylistModel() = PlaylistModel(
    playlistId = playlistId,
    name = name,
    creationDate = creationDate,
    userId = userId,
    userName = userName,
    zip = zip
)