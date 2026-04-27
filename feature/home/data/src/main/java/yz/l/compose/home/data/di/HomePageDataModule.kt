package yz.l.compose.home.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.feature.common.room.api.RemoteRepoApi
import yz.l.compose.feature.common.room.dao.HomePageDao
import yz.l.compose.feature.common.room.provider.RoomTransactionRunnerProvider
import yz.l.compose.home.data.repoapi.HomePageRepoApi
import yz.l.compose.home.data.repoprovider.HomePageRepo

/**
 * desc:
 * created by liyuzheng on 2026/4/27 19:14
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DiscoverDataModule {
    @Provides
    fun providesDiscoverRepo(
        homePageDao: HomePageDao,
        remoteRepo: RemoteRepoApi,
        transactionRunnerProvider: RoomTransactionRunnerProvider,
    ): HomePageRepoApi = HomePageRepo(homePageDao, remoteRepo, transactionRunnerProvider)
}

