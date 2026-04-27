package yz.l.compose.home.data.di

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import yz.l.compose.home.data.mediator.HomePageMediator

/**
 * desc:
 * created by liyuzheng on 2026/4/10 13:14
 */
@AssistedFactory
interface HomePageMediatorFactory {
    fun create(
        @Assisted("remoteName") remoteName: String = "HomePageMediator",
        @Assisted("initializeClear") initializeClear: Boolean = true
    ): HomePageMediator
}