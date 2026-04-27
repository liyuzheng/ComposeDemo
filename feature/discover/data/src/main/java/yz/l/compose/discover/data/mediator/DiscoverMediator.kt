package yz.l.compose.discover.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import timber.log.Timber
import yz.l.compose.discover.data.BigDiscoverMultipleAppsCard
import yz.l.compose.discover.data.DiscoverCardDetail
import yz.l.compose.discover.data.DiscoverCardModel
import yz.l.compose.discover.data.SmallDiscoverCard
import yz.l.compose.discover.data.repoapi.DiscoverRepoApi
import yz.l.compose.feature.common.paging.BaseRemoteMediator
import yz.l.compose.feature.common.room.api.RemoteRepoApi
import yz.l.compose.feature.common.room.entity.DiscoverCardTable
import yz.l.network.ext.repo
import yz.l.network.ext.request

/**
 * desc:
 * created by liyuzheng on 2026/4/7 19:31
 */
class DiscoverMediator @AssistedInject constructor(
    override val remoteRepo: RemoteRepoApi,
    private val discoverRepo: DiscoverRepoApi,
    @Assisted("queryString") val queryString: String,
    @Assisted("remoteName") override val remoteName: String,
    @Assisted("initializeClear") override val initializeClear: Boolean = true
) :
    BaseRemoteMediator<DiscoverCardTable, DiscoverCardDetail>(
        remoteRepo = remoteRepo
    ) {
    init {
        Timber.v("create DiscoverMediator name = $remoteName   initializeClear = $initializeClear")
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun initialize(): InitializeAction {
        val initializeAction = super.initialize()
        val model = DiscoverCardModel(
            cards = mutableListOf(
                DiscoverCardDetail(
                    cardType = 1,
                    id = 1,
                    card = BigDiscoverMultipleAppsCard()
                ),
                DiscoverCardDetail(
                    cardType = 2,
                    id = 2,
                    card = SmallDiscoverCard()
                ),
            )
        )
        discoverRepo.insertAndUpdateNext(model, remoteName, false)
        return initializeAction
    }

    override suspend fun load(
        loadKey: String,
        loadType: LoadType,
        pageConfig: PagingConfig
    ): Boolean {
        if (loadType == LoadType.REFRESH) {
            delay(2000)
        }
        val repo = repo {
            api(loadKey.ifBlank { queryString })
        }
        Timber.v("loadtype $loadType $loadKey")
        val result = repo.request<DiscoverCardModel>()

        discoverRepo.insertAndUpdateNext(result, remoteName, loadType == LoadType.REFRESH)
        Timber.v("result next ${result.next} ${result.next.isNullOrBlank()} $loadType")
        return result.next.isNullOrBlank()
    }

    override suspend fun prepend(
        loadKey: String,
        loadType: LoadType,
        pageConfig: PagingConfig
    ): Boolean {
        val repo = repo {
            api(loadKey)
        }
        Timber.v("prepend loadType $loadType $loadKey")
        val result = repo.request<DiscoverCardModel>()
        discoverRepo.insertAndUpdateNext(result, remoteName, loadType == LoadType.REFRESH)
        Timber.v("result next ${result.prev} ${result.prev.isNullOrBlank()} $loadType")
        return result.prev.isNullOrBlank()
    }

    override suspend fun clearLocalData() {
        super.clearLocalData()
        discoverRepo.clearLocalDataByRemoteNameAsync(remoteName)
    }
}