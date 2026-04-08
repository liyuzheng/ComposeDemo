package yz.l.compose.feature.common.room.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yz.l.compose.feature.common.room.AppDataBase
import javax.inject.Singleton

/**
 * desc:
 * created by liyuzheng on 2026/4/7 16:53
 */
@Module
@InstallIn(SingletonComponent::class)
// 这里使用了 ApplicationComponent，因此 NetworkModule 绑定到 Application 的生命周期。
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDataBase(application: Application): AppDataBase {
        return Room
            .databaseBuilder(application, AppDataBase::class.java, "androidComposeDemo.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration(true)
            .enableMultiInstanceInvalidation()
            .build()
    }
}