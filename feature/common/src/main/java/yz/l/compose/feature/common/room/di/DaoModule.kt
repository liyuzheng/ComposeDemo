package yz.l.compose.feature.common.room.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.feature.common.room.AppDatabase
import yz.l.compose.feature.common.room.dao.DiscoverCardDao
import yz.l.compose.feature.common.room.dao.RemoteDao

/**
 * desc:
 * created by liyuzheng on 2026/4/7 18:28
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesDiscoverDao(
        database: AppDatabase,
    ): DiscoverCardDao = database.discoverDao()

    @Provides
    fun providesRemote(
        database: AppDatabase,
    ): RemoteDao = database.remoteDao()
}
