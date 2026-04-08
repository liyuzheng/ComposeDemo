package yz.l.compose.home.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import timber.log.Timber
import yz.l.compose.api.entrypoints.LotteryEntryPoint
import yz.l.compose.discover.api.entrypoints.DiscoverEntryPoint
import yz.l.compose.feature.common.component.ShowStatusBar
import yz.l.compose.feature.common.injectModule
import yz.l.compose.home.impl.component.BottomNavigationBar

/**
 * desc:
 * created by liyuzheng on 2026/4/6 17:22
 */
@Preview
@Composable
fun HomeScreen() {
    ShowStatusBar(true)
    val lotteryApi = injectModule(LotteryEntryPoint::class.java).lotteryApi()
    val discoverApi = injectModule(DiscoverEntryPoint::class.java).discoverApi()
    val pagerState = rememberPagerState(1, pageCount = { 4 })
    val scope = rememberCoroutineScope()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        content = { _ ->
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false, // 禁用左右滑动，只允许点击底部栏切换
                beyondViewportPageCount = 1 // 💡 预加载并保留相邻页面的状态
            ) { page ->
                when (page) {
                    1 -> discoverApi.GetDiscoverScreen()
                    2 -> lotteryApi.GetLotteryScreen("2")
                    3 -> lotteryApi.GetLotteryScreen("3")
                    4 -> lotteryApi.GetLotteryScreen("4")
                    else -> lotteryApi.GetLotteryScreen("1")
                }
            }
        },
        bottomBar = {
            BottomNavigationBar { index ->
                scope.launch {
                    pagerState.scrollToPage(index)
                }
                Timber.v("sss")
            }
        })
}

