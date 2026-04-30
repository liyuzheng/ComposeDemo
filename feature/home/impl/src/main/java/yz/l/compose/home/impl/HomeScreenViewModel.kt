package yz.l.compose.home.impl

import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    val pagingItems = fetchHomeItems()

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