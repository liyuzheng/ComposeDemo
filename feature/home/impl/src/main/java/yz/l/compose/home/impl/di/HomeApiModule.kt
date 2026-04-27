package yz.l.compose.home.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.home.api.HomeApi
import yz.l.compose.home.impl.provider.HomeApiProvider

/**
 * desc:
 * created by liyuzheng on 2026/4/7 14:42
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class HomeApiModule {
    @Binds
    abstract fun bindHomeApi(impl: HomeApiProvider): HomeApi
}