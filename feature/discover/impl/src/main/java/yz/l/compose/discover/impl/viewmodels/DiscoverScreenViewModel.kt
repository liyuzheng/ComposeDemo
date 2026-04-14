package yz.l.compose.discover.impl.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import yz.l.compose.discover.data.DiscoverCardDetail
import yz.l.compose.discover.data.di.DiscoverMediatorFactory
import yz.l.compose.discover.data.mapper.toDiscoverCardDetail
import yz.l.compose.discover.data.repoapi.DiscoverRepoApi
import yz.l.compose.feature.common.common.BaseIntent
import yz.l.compose.feature.common.common.BaseViewModel
import yz.l.compose.feature.common.paging.ext.request
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/7 20:26
 */
@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    private val discoverRepo: DiscoverRepoApi,
    private val factory: DiscoverMediatorFactory
) : BaseViewModel() {

    val searchObs = MutableStateFlow("")
    val pagingItems = searchObs.flatMapLatest {
        val page = it.ifBlank { "1" }
        val api = "https://liyuzheng.github.io/bigfile.io/compose/discover$page.html"
        fetchDiscoverData(api)
    }.cachedIn(viewModelScope)

    fun fetchDiscoverData(queryString: String): Flow<PagingData<DiscoverCardDetail>> {
        return factory.create(queryString = queryString).request(
            viewModel = this,
            fetchData = {
                discoverRepo.getDiscoverCardPagingSource("DiscoverMediator")
            }, transform = {
                it.toDiscoverCardDetail()
            }
        )
    }
}

sealed class DiscoverIntent() : BaseIntent {
    object FetchIntent : DiscoverIntent()
}