package yz.l.compose.home.data.mapper

import yz.l.compose.feature.common.room.entity.HomeItem
import yz.l.compose.home.data.HomeItemModel

/**
 * desc:
 * created by liyuzheng on 2026/4/27 21:14
 */
fun HomeItem.toHomeItemModel() = HomeItemModel(
    feedId = feed.feedId,
    type = feed.type,
    position = feed.position,
    radios = radios.map {
        it.toRadioModel()
    },
    tracks = tracks.map {
        it.toTrackModel()
    },
    playlist = playlist?.toPlaylistModel()

)