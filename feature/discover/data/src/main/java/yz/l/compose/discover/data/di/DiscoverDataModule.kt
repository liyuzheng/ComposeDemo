package yz.l.compose.discover.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.discover.data.repoapi.DiscoverRepoApi
import yz.l.compose.discover.data.repoprovider.DiscoverRepo
import yz.l.compose.feature.common.room.api.RemoteRepoApi
import yz.l.compose.feature.common.room.dao.DiscoverCardDao
import yz.l.compose.feature.common.room.provider.RoomTransactionRunnerProvider

/**
 * desc:
 * created by liyuzheng on 2026/4/9 17:33
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DiscoverDataModule {
    @Provides
    fun providesDiscoverRepo(
        discoverDao: DiscoverCardDao,
        remoteRepo: RemoteRepoApi,
        transactionRunnerProvider: RoomTransactionRunnerProvider,
    ): DiscoverRepoApi = DiscoverRepo(discoverDao, remoteRepo, transactionRunnerProvider)
}

