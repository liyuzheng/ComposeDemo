package yz.l.compose.home.data.mediator

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import yz.l.compose.feature.common.common.BaseDataResponse
import yz.l.compose.feature.common.paging.BaseRemoteMediator
import yz.l.compose.feature.common.room.api.RemoteRepoApi
import yz.l.compose.feature.common.room.entity.FeedItemRef
import yz.l.compose.feature.common.room.entity.HomeItem
import yz.l.compose.feature.common.room.entity.HomePageIndexEntity
import yz.l.compose.home.data.HomeItemModel
import yz.l.compose.home.data.PlaylistModel
import yz.l.compose.home.data.RadioModel
import yz.l.compose.home.data.TrackModel
import yz.l.compose.home.data.repoapi.HomePageRepoApi
import yz.l.core_tool.BuildConfig
import yz.l.network.ext.repo
import yz.l.network.ext.request

/**
 * desc:
 * created by liyuzheng on 2026/4/27 19:05
 */
class HomePageMediator @AssistedInject constructor(
    override val remoteRepo: RemoteRepoApi,
    private val homePageRepo: HomePageRepoApi,
    @Assisted("remoteName") override val remoteName: String,
    @Assisted("initializeClear") override val initializeClear: Boolean = true
) : BaseRemoteMediator<HomeItem, HomeItemModel>(remoteRepo = remoteRepo) {
    companion object {
        const val RADIO_URL =
            "https://api.jamendo.com/v3.0/radios/?client_id=${BuildConfig.clientId}&format=jsonpretty&limit=5&order=id"
        const val TRACK_URL =
            "https://api.jamendo.com/v3.0/tracks/?client_id=${BuildConfig.clientId}&format=jsonpretty&limit=6&groupby=artist_id&include=lyrics&order=popularity_week"

        const val PLAYLIST_DEFAULT_URL =
            "https://api.jamendo.com/v3.0/playlists/?client_id=${BuildConfig.clientId}&format=jsonpretty&order=creationdate_desc&limit=20"
    }

    override fun defaultRefreshLoadKey(remoteName: String): String {
        return PLAYLIST_DEFAULT_URL
    }

    override suspend fun load(
        loadKey: String, loadType: LoadType, pageConfig: PagingConfig
    ): Boolean {
        var nextKey: String?
        if (loadType == LoadType.REFRESH) {
            val radioRepo = repo {
                api(RADIO_URL)
            }
            val trackRepo = repo {
                api(TRACK_URL)
            }
            val playlistRepo = repo {
                api(loadKey.ifBlank { PLAYLIST_DEFAULT_URL })
            }

            val (radios, tracks, playlists) = coroutineScope {
                val radios = async { radioRepo.request<BaseDataResponse<List<RadioModel>>>() }
                val tracks = async { trackRepo.request<BaseDataResponse<List<TrackModel>>>() }
                val playlists =
                    async { playlistRepo.request<BaseDataResponse<List<PlaylistModel>>>() }
                Triple(radios.await(), tracks.await(), playlists.await())
            }
            val homeItems = mutableListOf<HomePageIndexEntity>()
            val homeItemRefs = mutableListOf<FeedItemRef>()
            val searchItem = HomePageIndexEntity(0, "search", remoteName, 0)
            homeItems.add(searchItem)
            val radiosItem = HomePageIndexEntity(1, "radio", remoteName, 1)
            homeItems.add(radiosItem)
            val feedItemRefs = radios.results?.map { radio ->
                FeedItemRef(remoteName, 1, radio.radioId, 0)
            }
            feedItemRefs?.run {
                homeItemRefs.addAll(this)
            }
            val libItem = HomePageIndexEntity(2, "libs", remoteName, 2)
            homeItems.add(libItem)
            val trackItem = HomePageIndexEntity(3, "track", remoteName, 3)
            homeItems.add(trackItem)
            val tracksRefs = tracks.results?.map { track ->
                FeedItemRef(remoteName, 3, track.trackId, 1)
            }
            tracksRefs?.run {
                homeItemRefs.addAll(this)
            }
            val titleItem = HomePageIndexEntity(4, "title", remoteName, 4)
            homeItems.add(titleItem)

            playlists.results?.forEachIndexed { index, model ->
                val feedId = index + 5L
                val playlistItem =
                    HomePageIndexEntity(feedId, "playlist", remoteName, feedId.toInt())
                homeItems.add(playlistItem)
                homeItemRefs.add(FeedItemRef(remoteName, feedId, model.playlistId, 2))
            }

            nextKey = playlists.headers?.next
            homePageRepo.initInsert(
                remoteName,
                homeItems,
                homeItemRefs,
                radios.results,
                tracks.results,
                playlists.results,
                nextKey
            )

        } else {
            val playlistRepo = repo {
                api(loadKey)
            }
            val result = playlistRepo.request<BaseDataResponse<List<PlaylistModel>>>()

            nextKey = result.headers?.next
            homePageRepo.insertPlaylists(remoteName, result, nextKey)
        }
        return nextKey.isNullOrBlank()
    }

    override suspend fun clearLocalData() {
        super.clearLocalData()
        homePageRepo.clearData(remoteName)
    }
}