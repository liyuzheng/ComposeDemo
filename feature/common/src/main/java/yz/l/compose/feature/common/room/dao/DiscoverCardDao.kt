package yz.l.compose.feature.common.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import yz.l.compose.feature.common.room.entity.DiscoverCardTable

/**
 * desc:
 * created by liyuzheng on 2026/4/7 17:53
 */
@Dao
interface DiscoverCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inertDiscoverCard(calendarEntity: DiscoverCardTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsync(mList: List<DiscoverCardTable>)

    @Query("SELECT * FROM discover_cards WHERE id = :id ")
    suspend fun getDiscoverCardById(id: Int): DiscoverCardTable?

    @Query("SELECT * FROM discover_cards WHERE remoteName = :remoteName ORDER BY id ASC")
    fun getDiscoverCardPagingSource(remoteName:String):PagingSource<Int, DiscoverCardTable>

    @Query("DELETE FROM discover_cards WHERE remoteName = :remoteName")
    suspend fun clearLocalDataByRemoteNameAsync(remoteName: String)

    @Query("SELECT * FROM discover_cards WHERE remoteName = :remoteName ORDER BY id")
    fun getAllDiscoverCards(remoteName:String): List<DiscoverCardTable>


}