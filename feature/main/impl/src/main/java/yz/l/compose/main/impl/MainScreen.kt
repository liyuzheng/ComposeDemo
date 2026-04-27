package yz.l.compose.main.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import timber.log.Timber
import yz.l.compose.api.entrypoints.LotteryEntryPoint
import yz.l.compose.discover.api.entrypoints.DiscoverEntryPoint
import yz.l.compose.feature.common.component.SystemStatusBar
import yz.l.compose.feature.common.injectModule
import yz.l.compose.game.api.entrypooints.GameEntryPoint
import yz.l.compose.home.api.entrypoints.HomeEntryPoint
import yz.l.compose.main.impl.component.LightFlowLottieNavBar
import yz.l.compose.main.impl.component.NavItem

/**
 * desc:
 * created by liyuzheng on 2026/4/6 17:22
 */
@Preview
@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    val lotteryApi = injectModule(LotteryEntryPoint::class.java).lotteryApi()
    val discoverApi = injectModule(DiscoverEntryPoint::class.java).discoverApi()
    val gameApi = injectModule(GameEntryPoint::class.java).gameApi()
    val homeApi = injectModule(HomeEntryPoint::class.java).homeApi()
    val pagerState = rememberPagerState(0, pageCount = { 5 })
    val scope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val navItems = listOf(
        NavItem("探索", R.raw.ic_home),
        NavItem("游戏11222", R.raw.ic_controller),
        NavItem("应用", R.raw.ic_packet),
        NavItem("我的", R.raw.ic_profile)
    )
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        content = { _ ->
            SystemStatusBar(true, pagerState.currentPage % 2 == 0)
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false, // 禁用左右滑动，只允许点击底部栏切换
                beyondViewportPageCount = 1 // 💡 预加载并保留相邻页面的状态
            ) { page ->
                when (page) {
                    0 -> {
                        homeApi.GetHomeScreen()
                    }

                    1 -> {
                        gameApi.GetGameScreen()
                    }

                    2 -> {
                        discoverApi.GetDiscoverScreen()
                    }

                    3 -> {
                        lotteryApi.GetLotteryScreen("4")
                    }

                    else -> lotteryApi.GetLotteryScreen("1")
                }
            }
        },
        bottomBar = {
            LightFlowLottieNavBar(
                modifier = Modifier.navigationBarsPadding(),
                items = navItems,
                selectedIndex = uiState.value.selectedIndex,
                onItemSelected = { index ->
                    viewModel.dispatchIntent(HomeIntent.SelectedIndexChangedIntent(index))
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                    Timber.v("123123124124124124124")
                }
            )
        })
}


@Composable
@Preview
fun PreviewBottomBar() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val navItems = listOf(
        NavItem("首页", R.raw.ic_home),
        NavItem("搜索", R.raw.ic_home),
        NavItem("应用", R.raw.ic_home),
        NavItem("我的", R.raw.ic_home)
    )
    LightFlowLottieNavBar(
        items = navItems,
        selectedIndex = selectedIndex,
        onItemSelected = {
            selectedIndex = it
        }
    )
}

