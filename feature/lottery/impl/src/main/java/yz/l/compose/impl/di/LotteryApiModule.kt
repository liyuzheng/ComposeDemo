package yz.l.compose.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.api.LotteryApi
import yz.l.compose.impl.provider.LotteryApiProvider

/**
 * desc:
 * created by liyuzheng on 2026/4/7 11:01
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class LotteryApiModule {
    @Binds
    abstract fun bindLotteryApi(impl: LotteryApiProvider): LotteryApi
}