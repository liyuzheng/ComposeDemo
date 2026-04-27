package yz.l.compose.home.data.repoprovider

import androidx.paging.PagingSource
import yz.l.compose.feature.common.common.BaseDataResponse
import yz.l.compose.feature.common.paging.RemoteModel
import yz.l.compose.feature.common.room.api.DatabaseTransactionRunner
import yz.l.compose.feature.common.room.api.RemoteRepoApi
import yz.l.compose.feature.common.room.dao.HomePageDao
import yz.l.compose.feature.common.room.entity.FeedItemRef
import yz.l.compose.feature.common.room.entity.HomeItem
import yz.l.compose.feature.common.room.entity.HomePageIndexEntity
import yz.l.compose.home.data.PlaylistModel
import yz.l.compose.home.data.RadioModel
import yz.l.compose.home.data.TrackModel
import yz.l.compose.home.data.mapper.toPlaylistEntity
import yz.l.compose.home.data.mapper.toRadioEntity
import yz.l.compose.home.data.mapper.toTrackEntity
import yz.l.compose.home.data.repoapi.HomePageRepoApi
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/27 19:31
 */
class HomePageRepo @Inject constructor(
    private val dao: HomePageDao,
    private val remoteRepo: RemoteRepoApi,
    private val databaseTransactionRunner: DatabaseTransactionRunner
) : HomePageRepoApi {
    override suspend fun initInsert(
        remoteName: String,
        homeIndexes: List<HomePageIndexEntity>,
        refs: List<FeedItemRef>,
        radios: List<RadioModel>?,
        tracks: List<TrackModel>?,
        playlists: List<PlaylistModel>?,
        next: String?
    ) {
        databaseTransactionRunner {
            dao.insertAllHomePageIndexes(homeIndexes)
            dao.insertAllRefs(refs)
            val radioEntities = radios?.map {
                it.toRadioEntity(remoteName)
            }
            radioEntities?.run {
                dao.insertAllRadios(this)
            }
            val trackEntities = tracks?.map {
                it.toTrackEntity(remoteName)
            }
            trackEntities?.run {
                dao.insertAllTracks(this)
            }
            val playlistEntities = playlists?.map {
                it.toPlaylistEntity(remoteName)
            }
            playlistEntities?.run {
                dao.insertAllPlaylists(this)
            }
            remoteRepo.insertAsync(RemoteModel(remoteName, next.toString()))
        }
    }

    override suspend fun insertPlaylists(
        remoteName: String,
        items: BaseDataResponse<List<PlaylistModel>>,
        next: String?
    ) {
        databaseTransactionRunner {
            val homeItems = mutableListOf<HomePageIndexEntity>()
            val homeItemRefs = mutableListOf<FeedItemRef>()
            val lastFeedId = dao.getLastFeedId() ?: 0L
            items.results?.forEachIndexed { index, model ->
                val feedId = index + lastFeedId
                val playlistItem = HomePageIndexEntity(feedId, "playlist", remoteName, 5)
                homeItems.add(playlistItem)
                homeItemRefs.add(FeedItemRef(remoteName, feedId, model.playlistId, 2))
            }
            dao.insertAllHomePageIndexes(homeItems)
            dao.insertAllRefs(homeItemRefs)
            items.results?.map {
                it.toPlaylistEntity(remoteName)
            }?.run {
                dao.insertAllPlaylists(this)
            }
            remoteRepo.insertAsync(RemoteModel(remoteName, next.toString()))
        }
    }

    override fun getHomeItemPagingItems(remoteName: String): PagingSource<Int, HomeItem> {
        return dao.getHomeItemPagingSource()
    }
}