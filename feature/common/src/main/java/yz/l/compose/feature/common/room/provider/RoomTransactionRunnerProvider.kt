package yz.l.compose.feature.common.room.provider

import androidx.room.withTransaction
import yz.l.compose.feature.common.room.AppDatabase
import yz.l.compose.feature.common.room.api.DatabaseTransactionRunner
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/9 16:59
 */
class RoomTransactionRunnerProvider @Inject constructor(private val db: AppDatabase) :
    DatabaseTransactionRunner {
    override suspend fun <T> invoke(block: suspend () -> T): T {
        return db.withTransaction { block() }
    }
}