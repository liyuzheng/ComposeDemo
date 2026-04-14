package yz.l.compose.feature.common.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import timber.log.Timber
import yz.l.compose.feature.common.room.api.RemoteRepoApi
import yz.l.network.ResponseEmpty
import yz.l.network.ext.toResponseException

/**
 * desc:
 * created by liyuzheng on 2026/4/7 18:36
 */
@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<V : Any, T : Any>(
    open val remoteName: String = "",
    open val remoteRepo: RemoteRepoApi,
    open val initializeClear: Boolean = true
) : RemoteMediator<Int, V>() {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun initialize(): InitializeAction {
        Timber.v("InitializeAction clearLocalData $initializeClear")
        if (initializeClear)
            clearLocalData()
        return super.initialize()
    }

    /**
     * 获取loadKey,也就是页码数
     * 本方法只有在LoadType.APPEND时回调
     * LoadType.PREPEND 本项目暂时不涉及到，所以直接回调
     * LoadType.REFRESH 回调{@defaultRefreshLoadKey()}方法
     */
    open suspend fun initLoadKey(
        remoteName: String,
    ): String? {
        return remoteRepo.getRemoteKeysAsync(remoteName)?.next
    }

    /**
     * 刷新时页码数，通常情况为""
     */
    open fun defaultRefreshLoadKey(remoteName: String) = ""

    /**
     * 网络获取数据,并存入数据库中。
     * @return endOfPaginationReached true 列表没有更多数据了，false 列表还有更多数据
     */
    abstract suspend fun load(
        loadKey: String,
        loadType: LoadType,
        pageConfig: PagingConfig
    ): Boolean

    /**
     * 刷新时空页面的的回调Exception
     * @return ResponseEmptyException 配合EmptyViewholder使用，不同情况使用不同样式
     */
    open fun customEmpty(remoteName: String): ResponseEmpty {
        return ResponseEmpty()
    }

    open suspend fun clearLocalData() {}

    final override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, V>
    ): MediatorResult {
        val loadKey: String = when (loadType) {
            LoadType.PREPEND -> initPrependKey(remoteName) ?: return MediatorResult.Success(true)
            LoadType.APPEND -> initLoadKey(remoteName) ?: defaultRefreshLoadKey(remoteName)
            else -> defaultRefreshLoadKey(remoteName)
        }
//        return MediatorResult.Success(load(loadKey, loadType, state.config))
        return try {
            if (loadType == LoadType.PREPEND) {
                if (loadKey.isBlank()) return MediatorResult.Success(true)
                val endOfPaginationReached =
                    prepend(loadKey, loadType, state.config)
                MediatorResult.Success(endOfPaginationReached)
            } else {
                val endOfPaginationReached =
                    load(loadKey, loadType, state.config)

                if (endOfPaginationReached && loadType == LoadType.REFRESH) {
                    MediatorResult.Error(customEmpty(remoteName))
                } else {
                    MediatorResult.Success(endOfPaginationReached)
                }
            }

        } catch (ex: Exception) {
            val e = ex.toResponseException()
            Timber.e("load error $e")
            MediatorResult.Error(e)
        }
    }

    open suspend fun prepend(
        loadKey: String,
        loadType: LoadType,
        pageConfig: PagingConfig
    ): Boolean {
        return true
    }

    open suspend fun initPrependKey(
        remoteName: String,
    ): String? {
        return remoteRepo.getRemoteKeysAsync(remoteName)?.prepend
    }
}