package yz.l.compose.discover.api.entrypoints

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.discover.api.DiscoverApi

/**
 * desc:
 * created by liyuzheng on 2026/4/7 14:40
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface DiscoverEntryPoint {
    fun discoverApi(): DiscoverApi
}