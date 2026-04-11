package yz.l.compose.discover.impl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import yz.l.compose.discover.data.di.DiscoverMediatorFactory
import yz.l.compose.discover.data.mapper.toDiscoverCardDetail
import yz.l.compose.discover.data.repoapi.DiscoverRepoApi
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/7 20:26
 */
@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    private val discoverRepo: DiscoverRepoApi,
    factory: DiscoverMediatorFactory
) : ViewModel() {
    @OptIn(ExperimentalPagingApi::class)
    val discoverCardFlow by lazy {
        Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = factory.create()
        ) {
            discoverRepo.getDiscoverCardPagingSource("DiscoverMediator") // 这里的 PagingSource 由 Room 自动生成
        }.flow.map { pagingData ->
            pagingData.map {
                it.toDiscoverCardDetail()
            }
        }.cachedIn(viewModelScope)
    }
}

//map {
//    it.map { r ->
//        r.toDiscoverCardDetail()
//    }
//}