package yz.l.compose.api.entrypoints

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.api.LotteryApi

/**
 * desc:
 * created by liyuzheng on 2026/4/7 10:57
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface LotteryEntryPoint {
    fun lotteryApi(): LotteryApi
}