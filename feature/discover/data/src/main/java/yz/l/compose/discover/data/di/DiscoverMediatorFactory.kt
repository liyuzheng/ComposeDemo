package yz.l.compose.discover.data.di

import dagger.assisted.AssistedFactory
import yz.l.compose.discover.data.mediator.DiscoverMediator

/**
 * desc:
 * created by liyuzheng on 2026/4/10 13:14
 */
@AssistedFactory
interface DiscoverMediatorFactory {
    fun create(
        remoteName: String = "DiscoverMediator",
        initializeClear: Boolean = true
    ): DiscoverMediator
}