package yz.l.compose.discover.data.repoprovider

import androidx.paging.PagingSource
import yz.l.compose.discover.data.DiscoverCardModel
import yz.l.compose.discover.data.mapper.toDiscoverCardTable
import yz.l.compose.discover.data.repoapi.DiscoverRepoApi
import yz.l.compose.feature.common.paging.RemoteModel
import yz.l.compose.feature.common.room.api.DatabaseTransactionRunner
import yz.l.compose.feature.common.room.api.RemoteRepoApi
import yz.l.compose.feature.common.room.dao.DiscoverCardDao
import yz.l.compose.feature.common.room.entity.DiscoverCardTable
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/9 16:51
 */
internal class DiscoverRepo @Inject constructor(
    private val dao: DiscoverCardDao,
    private val remoteRepo: RemoteRepoApi,
    private val databaseTransactionRunner: DatabaseTransactionRunner
) : DiscoverRepoApi {

    override suspend fun insertAndUpdateNext(
        model: DiscoverCardModel?,
        remoteName: String,
        clearData: Boolean
    ) {
        databaseTransactionRunner {
            if (clearData) {
                dao.clearLocalDataByRemoteNameAsync(remoteName)
            }
            val cardEntities = model?.cards?.map {
                it.toDiscoverCardTable(remoteName)
            }
            cardEntities?.run {
                dao.insertAllAsync(this)
            }

            remoteRepo.insertAsync(
                RemoteModel(
                    remoteName,
                    model?.next ?: "",
                    model?.prev ?: ""
                )
            )
        }
    }

    override fun getDiscoverCardPagingSource(remoteName: String): PagingSource<Int, DiscoverCardTable> =
        dao.getDiscoverCardPagingSource(remoteName)

    override suspend fun clearLocalDataByRemoteNameAsync(remoteName: String) {
        dao.clearLocalDataByRemoteNameAsync(remoteName)
    }

    override fun getAllDiscoverCards(remoteName: String): List<DiscoverCardTable> =
        dao.getAllDiscoverCards(remoteName)
}