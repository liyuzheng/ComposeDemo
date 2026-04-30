package yz.l.compose.home.impl

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import yz.l.compose.feature.common.common.BaseViewModel
import yz.l.compose.feature.common.paging.ext.request
import yz.l.compose.home.data.HomeItemModel
import yz.l.compose.home.data.di.HomePageMediatorFactory
import yz.l.compose.home.data.mapper.toHomeItemModel
import yz.l.compose.home.data.repoapi.HomePageRepoApi
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/25 16:41
 */
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val homePageRepo: HomePageRepoApi,
    private val factory: HomePageMediatorFactory
) : BaseViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val pagingItems = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = factory.create(),
        pagingSourceFactory = { homePageRepo.getHomeItemPagingItems("HomePageMediator") }
    ).flow.map { data ->
        data.map {
            it.toHomeItemModel()
        }
    }.cachedIn(viewModelScope) // 👈 在ViewModel作用域内缓存


    private fun fetchHomeItems(): Flow<PagingData<HomeItemModel>> {
        return factory.create().request(
            viewModel = this,
            fetchData = {
                homePageRepo.getHomeItemPagingItems("HomePageMediator")
            }, transform = {
                it.toHomeItemModel()
            }
        )
    }
}