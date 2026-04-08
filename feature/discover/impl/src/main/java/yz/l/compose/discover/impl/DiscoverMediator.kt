package yz.l.compose.discover.impl

import androidx.paging.LoadType
import androidx.room.withTransaction
import kotlinx.coroutines.delay
import timber.log.Timber
import yz.l.compose.discover.data.DiscoverCardDetail
import yz.l.compose.discover.data.DiscoverCardModel
import yz.l.compose.discover.data.mapper.toDiscoverCardTable
import yz.l.compose.feature.common.mapper.toRemoteEntity
import yz.l.compose.feature.common.paging.BaseRemoteMediator
import yz.l.compose.feature.common.paging.RemoteModel
import yz.l.compose.feature.common.room.AppDataBase
import yz.l.compose.feature.common.room.dao.DiscoverCardDao
import yz.l.compose.feature.common.room.dao.RemoteDao
import yz.l.compose.feature.common.room.entity.DiscoverCardTable
import yz.l.network.ext.repo
import yz.l.network.ext.request

/**
 * desc:
 * created by liyuzheng on 2026/4/7 19:31
 */
class DiscoverMediator(
    override val remoteDao: RemoteDao,
    private val discoverDao: DiscoverCardDao,
    private val appDataBase: AppDataBase,
    override val remoteName: String,
    override val clearBeforeLoad: Boolean = true
) :
    BaseRemoteMediator<DiscoverCardTable, DiscoverCardDetail>(
        remoteDao = remoteDao
    ) {

    override suspend fun load(
        loadKey: String,
        loadType: LoadType,
        pageConfig: androidx.paging.PagingConfig
    ): Boolean {
        delay(2000)
        val url =
            loadKey.ifBlank { "https://liyuzheng.github.io/bigfile.io/compose/discover1.html" }
        Timber.v("load start $url")

        val repo = repo {
            api { url }
        }
        val result = repo.request<DiscoverCardModel>()
        Timber.v("result $result ${result.cards.size}")
        val lotteryEntities = result.cards.map {
            it.toDiscoverCardTable(remoteName)
        }
        appDataBase.withTransaction {
            discoverDao.insertAllAsync(lotteryEntities)
            remoteDao.insertAsync(RemoteModel(remoteName, result.next.toString()).toRemoteEntity())
        }
        val name = remoteDao.getRemoteKeysAsync(remoteName)
        val list = discoverDao.getAllDiscoverCards(remoteName)
        list.forEach { item ->
            Timber.v("all data $remoteName $name $item")
        }
        Timber.v("result next ${result.next} ${result.next.isNullOrBlank()}")
        return result.next.isNullOrBlank()
    }

    override suspend fun clearLocalData() {
        super.clearLocalData()
        discoverDao.clearLocalDataByRemoteNameAsync(remoteName)
    }
}