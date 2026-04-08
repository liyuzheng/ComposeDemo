package yz.l.compose.discover.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.discover.api.DiscoverApi
import yz.l.compose.discover.impl.provider.DiscoverApiProvider

/**
 * desc:
 * created by liyuzheng on 2026/4/7 14:42
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DiscoverApiModule {
    @Binds
    abstract fun bindDiscoverApi(impl: DiscoverApiProvider): DiscoverApi
}