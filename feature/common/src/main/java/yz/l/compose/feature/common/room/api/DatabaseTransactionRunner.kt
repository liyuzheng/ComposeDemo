package yz.l.compose.feature.common.room.api

/**
 * desc:
 * created by liyuzheng on 2026/4/9 16:58
 */
interface DatabaseTransactionRunner {
    suspend operator fun <T> invoke(block: suspend () -> T): T
}