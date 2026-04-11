package yz.l.compose.feature.common.paging.repo

import yz.l.compose.feature.common.mapper.toRemoteEntity
import yz.l.compose.feature.common.paging.RemoteModel
import yz.l.compose.feature.common.room.api.RemoteRepoApi
import yz.l.compose.feature.common.room.dao.RemoteDao
import yz.l.compose.feature.common.room.entity.RemoteEntity
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/9 17:37
 */
internal class RemoteRepo @Inject constructor(private val dao: RemoteDao) : RemoteRepoApi {
    override suspend fun insertAsync(remote: RemoteModel) {
        dao.insertAsync(remote.toRemoteEntity())
    }

    override suspend fun getRemoteKeysAsync(remoteName: String): RemoteEntity? =
        dao.getRemoteKeysAsync(remoteName)

    override suspend fun clearRemoteKeys(remoteName: String) {
        dao.clearRemoteKeys(remoteName)
    }
}