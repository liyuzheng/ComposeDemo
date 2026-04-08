package yz.l.compose.impl.provider

import androidx.compose.runtime.Composable
import yz.l.compose.api.LotteryApi
import yz.l.compose.impl.LotteryScreen
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/7 10:48
 */
class LotteryApiProvider @Inject constructor() : LotteryApi {
    @Composable
    override fun GetLotteryScreen(id: String) {
        LotteryScreen(id) {

        }
    }
}