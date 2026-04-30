package yz.l.compose.home.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import timber.log.Timber
import yz.l.compose.feature.common.component.AppTopBar
import yz.l.compose.feature.common.component.refreshlayout.PagingRefreshLayout
import yz.l.compose.home.data.RadioModel
import yz.l.compose.home.data.TrackModel

/**
 * desc:
 * created by liyuzheng on 2026/4/25 16:35
 */

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val listState = rememberLazyListState()
    val shouldHideTopBar by remember {
        derivedStateOf {
            val headerInfo = listState.layoutInfo.visibleItemsInfo
                .firstOrNull { it.key == "my_sticky" }
            (headerInfo?.offset ?: 0) < 0
        }
    }
    Scaffold(topBar = {
        AppTopBar("首页", shouldHideTopBar)
    }, content = { innerPadding ->
        Timber.v("HomeScreen compose ${innerPadding.calculateTopPadding()}")
        val topOffset = (-40).dp // 想要的距离顶部的高度
        val density = LocalDensity.current
        val offsetPx = remember { with(density) { topOffset.roundToPx() } }

        // 监听第一个可见 stickyHeader 的信息
        val stickyHeaderOffset by remember {
            derivedStateOf {
                val headerInfo = listState.layoutInfo.visibleItemsInfo
                    .firstOrNull { it.key == "my_sticky" } // 使用唯一 key
                if (headerInfo == null) 0
                else {
                    // 当头部即将吸顶时，动态调整偏移
                    val offsetToTop = headerInfo.offset
                    if (offsetToTop < offsetPx) {
                        offsetPx - offsetToTop
                    } else {
                        0
                    }
                }
            }
        }
        val pagingItems = viewModel.pagingItems.collectAsLazyPagingItems()
        PagingRefreshLayout(
            cState = listState,
            modifier = Modifier.padding(horizontal = 24.dp),
            innerPadding = innerPadding,
            pagingItems = pagingItems
        ) {
            if (pagingItems.itemCount > 5) {
                item(key = "search", contentType = { "search" }) {
                    SearchBar()
                }
            }
            if (pagingItems.itemCount > 5) {
                val item = pagingItems.peek(1)
                item(key = "radio", contentType = { "radio" }) {
                    RadioCard(item?.radios)
                }
            }
            if (pagingItems.itemCount > 5) {
                libsBar(offsetProvider = { stickyHeaderOffset })
//                stickyHeader(key = "my_sticky", contentType = { "my_sticky" }) {
//                    Box(
//                        modifier = Modifier
//                            .height(40.dp)
//                            .fillMaxWidth()
//                            .offset { IntOffset(0, stickyHeaderOffset) } // 动态偏移
//                            .background(Color.Gray)
//                            .padding(16.dp)) {
//                        Text("吸顶头部（距离顶部 ${stickyHeaderOffset}）")
//                    }
//                }
            }
            if (pagingItems.itemCount > 5) {
                val item = pagingItems.peek(3)
                item(key = "track", contentType = { "track" }) {
                    TrackCard(item?.tracks)
                }
            }
            if (pagingItems.itemCount > 5) {
                item(key = "title", contentType = { "title" }) {
                    TitleBar()
                }
            }

            items(
                (pagingItems.itemCount - 5).coerceAtLeast(0),
                key = pagingItems.itemKey { it.feedId }, // 必须加 key，优化性能
            ) { index ->
                Timber.v("home recom")
                val item = pagingItems[index]
                if (item != null) {
                    PlaylistCard(item.feedId.toString())
                }
            }

//            for (index in 0 until itemCount) {
//                val item = pagingItems[index]
//                when (item?.type) {
//                    "search" -> item(key = item.feedId, contentType = { "search" }) { SearchBar() }
//                    "radio" -> item(
//                        key = item.feedId,
//                        contentType = { "radio" }) { RadioCard(item.radios) }
//
//                    "libs" -> stickyHeader(key = item.feedId, contentType = { "libs" }) {
//                        LibsBar(stickyHeaderOffset)
//                    }
//
//                    "title" -> item(key = item.feedId, contentType = { "title" }) { TitleBar() }
//                    "playlist" -> item(key = item.feedId, contentType = { "playlist" }) {
//                        PlaylistCard(item.playlist)
//                    }
//                }
//            }
        }
    })
}

@Composable
@Preview
fun SearchBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()              // 占据整行宽度
            .height(40.dp)               // 固定高度
            .clip(CircleShape)           // 使用 CircleShape 实现完全圆角（胶囊形）
            .background(Color(0xFFF5F5F5)) // 背景色
            .padding(horizontal = 16.dp), // 两端的内边距
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = yz.l.compose.feature.common.R.drawable.bg_discover_search),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color(0xFF999999)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "歌曲、歌手等更多内容",
            fontSize = 14.sp,
            color = Color(0xFF999999),
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)) // 解决部分手机文字垂直居中偏移
        )
    }
}

@Composable
fun TitleBar() {
    Text(
        text = "title",
        fontSize = 12.sp,
        lineHeight = 12.sp,
        modifier = Modifier.padding(16.dp, 0.dp)
    )
}

fun LazyListScope.libsBar(offsetProvider: () -> Int) {
    stickyHeader(key = "my_sticky", contentType = { "my_sticky" }) {
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .offset { IntOffset(0, offsetProvider()) } // 动态偏移
                .background(Color.Gray)
                .padding(16.dp)) {
            Text("吸顶头部（距离顶部 ${offsetProvider()}）")
        }
    }
}

@Composable
fun RadioCard(radios: List<RadioModel>?) {
    val strs = radios?.map {
        it.name
    }
    Text(
        text = strs.toString(),
        fontSize = 12.sp,
        lineHeight = 12.sp,
        modifier = Modifier
            .height(40.dp)
            .padding(16.dp, 0.dp)
    )
}

@Composable
fun TrackCard(tracks: List<TrackModel>?) {
    val strs = tracks?.map {
        it.name
    }
    Text(
        text = strs.toString(),
        fontSize = 12.sp,
        lineHeight = 12.sp,
        modifier = Modifier
            .height(40.dp)
            .padding(16.dp, 0.dp)
    )
}

@Composable
fun PlaylistCard(str: String) {
    Text(
        text = str,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        modifier = Modifier
            .height(40.dp)
            .padding(16.dp, 0.dp)
    )
}