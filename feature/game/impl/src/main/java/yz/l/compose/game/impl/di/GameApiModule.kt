package yz.l.compose.game.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.game.api.GameApi
import yz.l.compose.game.impl.provider.GameApiProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class GameApiModule {
    @Binds
    abstract fun bindGameApi(impl: GameApiProvider): GameApi
}