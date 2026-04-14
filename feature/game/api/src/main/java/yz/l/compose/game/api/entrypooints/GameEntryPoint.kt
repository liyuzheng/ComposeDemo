package yz.l.compose.game.api.entrypooints

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.game.api.GameApi

/**
 * desc:
 * created by liyuzheng on 2026/4/7 14:40
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface GameEntryPoint {
    fun gameApi(): GameApi
}