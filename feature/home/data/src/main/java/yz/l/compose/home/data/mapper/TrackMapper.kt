package yz.l.compose.home.data.mapper

import yz.l.compose.feature.common.room.entity.TrackEntity
import yz.l.compose.home.data.TrackModel

/**
 * desc:
 * created by liyuzheng on 2026/4/27 18:59
 */
fun TrackModel.toTrackEntity(remoteName: String) = TrackEntity(
    remoteName = remoteName,
    trackId = trackId,
    name = name,
    duration = duration,
    artistId = artistId,
    artistName = artistName,
    albumName = albumName,
    albumId = albumId,
    position = position,
    releasedData = releasedData,
    albumImage = albumImage,
    audio = audio,
    image = image
)

fun TrackEntity.toTrackModel() = TrackModel(
    trackId = trackId,
    name = name,
    duration = duration,
    artistId = artistId,
    artistName = artistName,
    albumName = albumName,
    albumId = albumId,
    position = position,
    releasedData = releasedData,
    albumImage = albumImage,
    audio = audio,
    image = image
)