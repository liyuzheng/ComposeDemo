package yz.l.compose.home.impl

import android.app.Application
import android.os.Bundle
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import yz.l.compose.feature.common.common.BaseDataResponse
import yz.l.compose.feature.common.paging.common.DEFAULT_PAGER
import yz.l.compose.feature.common.paging.ext.request
import yz.l.compose.home.data.HomeItemModel
import yz.l.compose.home.data.RadioDetailModel
import yz.l.compose.home.data.di.HomePageMediatorFactory
import yz.l.compose.home.data.mapper.toHomeItemModel
import yz.l.compose.home.data.repoapi.HomePageRepoApi
import yz.l.compose.media.PlayManager
import yz.l.core_tool.BuildConfig
import yz.l.network.ext.repo
import yz.l.network.ext.request
import yz.l.network.onSuccess
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/25 16:41
 */
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val homePageRepo: HomePageRepoApi,
    private val factory: HomePageMediatorFactory,
    private val app: Application
) : AndroidViewModel(app) {
    val playManager = PlayManager.getInstance(app)

    @OptIn(ExperimentalPagingApi::class)
    val pagingItems = Pager(
        config = DEFAULT_PAGER,
        remoteMediator = factory.create(),
        pagingSourceFactory = { homePageRepo.getHomeItemPagingItems("HomePageMediator") }).flow.map { data ->
        data.map {
            it.toHomeItemModel()
        }
    }.cachedIn(viewModelScope) // 👈 在ViewModel作用域内缓存


    private fun fetchHomeItems(): Flow<PagingData<HomeItemModel>> {
        return factory.create().request(viewModel = this, fetchData = {
            homePageRepo.getHomeItemPagingItems("HomePageMediator")
        }, transform = {
            it.toHomeItemModel()
        })
    }

    fun fetchRadioById(id: String) {
        val repo = repo {
            api("https://api.jamendo.com/v3.0/radios/stream/")
            params { "client_id" to BuildConfig.clientId }
            params { "id" to id }
        }
        repo.request<BaseDataResponse<List<RadioDetailModel>>>(viewModelScope) { result ->
            result.onSuccess { r ->
                val radio = r.results?.getOrNull(0) ?: return@request
                Timber.v("fetchRadioById $id $radio")
                playManager.setMediaList(
                    mutableListOf(
                        MediaItem.Builder()
                            .setMediaId(radio.id.toString()) // 唯一标识符
                            .setUri(radio.stream.toUri())  // 核心：音频流地址
                            .setMediaMetadata(
                                MediaMetadata.Builder()
//                                    .setMediaType(MediaMetadata.MEDIA_TYPE_RADIO_STATION)
                                    .setTitle(radio.displayName)    // 标题（显示在通知栏）
                                    .setArtist(radio.name)          // 艺术家/电台名
                                    .setArtworkUri(radio.image.toUri()) // 封面图地址
                                    .setExtras(Bundle().apply {
                                        putString("a", radio.displayName)
                                    })
                                    .build()
                            )
                            .build()
                    )
                )
                playManager.playPause()
            }
        }
    }
}