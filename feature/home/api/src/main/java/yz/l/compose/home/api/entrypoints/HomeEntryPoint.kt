package yz.l.compose.home.api.entrypoints

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.home.api.HomeApi

/**
 * desc:
 * created by liyuzheng on 2026/4/25 16:33
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface HomeEntryPoint {
    fun homeApi(): HomeApi
}