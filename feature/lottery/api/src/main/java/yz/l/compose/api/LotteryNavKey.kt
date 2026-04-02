package yz.l.compose.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yz.l.core_router.Navigator

/**
 * desc:
 * created by liyuzheng on 2026/3/16 19:25
 */
@Serializable
data class LotteryNavKey(val id: String) : NavKey

fun Navigator.navigateToLottery(
    id: String,
) {
    navigate(LotteryNavKey(id))
}