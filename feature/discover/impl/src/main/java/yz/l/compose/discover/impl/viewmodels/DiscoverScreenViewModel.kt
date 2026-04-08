package yz.l.compose.discover.impl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import yz.l.compose.discover.impl.DiscoverMediator
import yz.l.compose.feature.common.room.AppDataBase
import yz.l.compose.feature.common.room.dao.DiscoverCardDao
import yz.l.compose.feature.common.room.dao.RemoteDao
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/7 20:26
 */
@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    private val remoteDao: RemoteDao,
    private val discoverDao: DiscoverCardDao,
    private val appDataBase: AppDataBase,
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val discoverCardFlow = Pager(
        config = PagingConfig(pageSize = 4, initialLoadSize = 8, enablePlaceholders = true),
        remoteMediator = DiscoverMediator(
            remoteDao,
            discoverDao,
            appDataBase,
            "DiscoverMediator",
            clearBeforeLoad = true
        )
    ) {
        discoverDao.getDiscoverCardPagingSource("DiscoverMediator") // 这里的 PagingSource 由 Room 自动生成
    }.flow.cachedIn(viewModelScope)
}

//map {
//    it.map { r ->
//        r.toDiscoverCardDetail()
//    }
//}