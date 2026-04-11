package yz.l.compose.feature.common.paging.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.feature.common.paging.repo.RemoteRepo
import yz.l.compose.feature.common.room.api.RemoteRepoApi
import yz.l.compose.feature.common.room.dao.RemoteDao

/**
 * desc:
 * created by liyuzheng on 2026/4/9 17:38
 */
@Module
@InstallIn(SingletonComponent::class)
class RemoteRepoModule {
    @Provides
    fun providesRemoteRepo(
        remoteDao: RemoteDao,
    ): RemoteRepoApi = RemoteRepo(remoteDao)
}