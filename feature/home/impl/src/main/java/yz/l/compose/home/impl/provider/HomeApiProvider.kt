package yz.l.compose.home.impl.provider

import androidx.compose.runtime.Composable
import yz.l.compose.home.api.HomeApi
import yz.l.compose.home.impl.HomeScreen
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/25 16:34
 */
class HomeApiProvider @Inject constructor() : HomeApi {
    @Composable
    override fun GetHomeScreen() {
        HomeScreen()
    }
}