package yz.l.compose.feature.common.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import yz.l.compose.feature.common.room.entity.FeedItemRef
import yz.l.compose.feature.common.room.entity.HomeItem
import yz.l.compose.feature.common.room.entity.HomePageIndexEntity
import yz.l.compose.feature.common.room.entity.PlaylistEntity
import yz.l.compose.feature.common.room.entity.RadioEntity
import yz.l.compose.feature.common.room.entity.TrackEntity

/**
 * desc:
 * created by liyuzheng on 2026/4/27 19:23
 */
@Dao
interface HomePageDao {
    @Transaction
    @Query("SELECT * FROM home_page_index_table ORDER BY position ASC")
    fun getHomeItemPagingSource(): PagingSource<Int, HomeItem>

    @Transaction
    @Query("SELECT MAX(feedId) FROM home_page_index_table")
    fun getLastFeedId(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRefs(mList: List<FeedItemRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHomePageIndexes(mList: List<HomePageIndexEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaylists(mList: List<PlaylistEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRadios(mList: List<RadioEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTracks(mList: List<TrackEntity>)
}