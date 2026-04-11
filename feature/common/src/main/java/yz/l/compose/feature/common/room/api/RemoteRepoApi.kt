package yz.l.compose.feature.common.room.api

import yz.l.compose.feature.common.paging.RemoteModel
import yz.l.compose.feature.common.room.entity.RemoteEntity

/**
 * desc:
 * created by liyuzheng on 2026/4/9 17:36
 */
interface RemoteRepoApi {
    suspend fun insertAsync(remote: RemoteModel)

    suspend fun getRemoteKeysAsync(remoteName: String): RemoteEntity?

    suspend fun clearRemoteKeys(remoteName: String)
}