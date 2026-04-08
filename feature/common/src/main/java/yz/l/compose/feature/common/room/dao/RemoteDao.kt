package yz.l.compose.feature.common.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import yz.l.compose.feature.common.room.entity.RemoteEntity

/**
 * desc:
 * created by liyuzheng on 2026/4/7 18:43
 */
@Dao
interface RemoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsync(remoteKeyEntity: RemoteEntity)

    @Query("SELECT * FROM RemoteEntity WHERE name = :remoteName ")
    suspend fun getRemoteKeysAsync(remoteName: String): RemoteEntity?

    @Query("DELETE FROM RemoteEntity WHERE name = :remoteName")
    suspend fun clearRemoteKeys(remoteName: String)
}