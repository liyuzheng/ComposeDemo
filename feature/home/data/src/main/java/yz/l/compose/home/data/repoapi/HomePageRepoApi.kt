package yz.l.compose.home.data.repoapi

import androidx.paging.PagingSource
import yz.l.compose.feature.common.common.BaseDataResponse
import yz.l.compose.feature.common.room.entity.FeedItemRef
import yz.l.compose.feature.common.room.entity.HomeItem
import yz.l.compose.feature.common.room.entity.HomePageIndexEntity
import yz.l.compose.home.data.PlaylistModel
import yz.l.compose.home.data.RadioModel
import yz.l.compose.home.data.TrackModel

/**
 * desc:
 * created by liyuzheng on 2026/4/27 19:30
 */
interface HomePageRepoApi {
    suspend fun initInsert(
        remoteName: String,
        homeIndexes: List<HomePageIndexEntity>,
        refs: List<FeedItemRef>,
        radios: List<RadioModel>?,
        tracks: List<TrackModel>?,
        playlists: List<PlaylistModel>?,
        next: String?
    )

    suspend fun insertPlaylists(
        remoteName: String,
        items: BaseDataResponse<List<PlaylistModel>>,
        next: String?
    )

    fun getHomeItemPagingItems(remoteName: String): PagingSource<Int, HomeItem>
}