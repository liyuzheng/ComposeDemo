package yz.l.compose.feature.common.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import yz.l.compose.feature.common.room.converters.DiscoverCardConvert
import yz.l.compose.feature.common.room.dao.DiscoverCardDao
import yz.l.compose.feature.common.room.dao.HomePageDao
import yz.l.compose.feature.common.room.dao.RemoteDao
import yz.l.compose.feature.common.room.entity.DiscoverCardTable
import yz.l.compose.feature.common.room.entity.FeedItemRef
import yz.l.compose.feature.common.room.entity.HomePageIndexEntity
import yz.l.compose.feature.common.room.entity.PlaylistEntity
import yz.l.compose.feature.common.room.entity.RadioEntity
import yz.l.compose.feature.common.room.entity.RemoteEntity
import yz.l.compose.feature.common.room.entity.TrackEntity

/**
 * desc:
 * created by liyuzheng on 2026/4/7 17:07
 */
@Database(
    entities = [DiscoverCardTable::class, RemoteEntity::class,
        HomePageIndexEntity::class, FeedItemRef::class,
        PlaylistEntity::class, RadioEntity::class,
        TrackEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(value = [DiscoverCardConvert::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun discoverDao(): DiscoverCardDao
    abstract fun remoteDao(): RemoteDao
    abstract fun homePageDao(): HomePageDao
}
