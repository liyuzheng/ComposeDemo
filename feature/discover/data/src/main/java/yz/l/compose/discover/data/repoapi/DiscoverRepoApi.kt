package yz.l.compose.discover.data.repoapi

import androidx.paging.PagingSource
import yz.l.compose.discover.data.DiscoverCardModel
import yz.l.compose.feature.common.room.entity.DiscoverCardTable

/**
 * desc:
 * created by liyuzheng on 2026/4/9 17:08
 */
interface DiscoverRepoApi {
    suspend fun insertAndUpdateNext(
        model: DiscoverCardModel?,
        remoteName: String,
        clearData: Boolean
    )

    fun getDiscoverCardPagingSource(remoteName: String): PagingSource<Int, DiscoverCardTable>

    suspend fun clearLocalDataByRemoteNameAsync(remoteName: String)

    fun getAllDiscoverCards(remoteName: String): List<DiscoverCardTable>
}