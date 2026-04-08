package yz.l.compose.discover.impl.provider

import androidx.compose.runtime.Composable
import yz.l.compose.discover.api.DiscoverApi
import yz.l.compose.discover.impl.DiscoverScreen
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/7 14:43
 */
class DiscoverApiProvider @Inject constructor() : DiscoverApi {
    @Composable
    override fun GetDiscoverScreen() {
        DiscoverScreen()
    }
}