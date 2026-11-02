package yz.l.compose.home.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
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
                    RadioCard({ item?.radios }, viewModel)
                }
            }
            if (pagingItems.itemCount > 5) {
                libsBar(offsetProvider = { stickyHeaderOffset })
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
fun RadioCard(data: () -> List<RadioModel>?, viewModel: HomeScreenViewModel) {
    val radios = data()
    Timber.v("RadioCard ${radios?.size}")
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { radios?.size ?: 0 })
    HorizontalPager(
        pageSize = PageSize.Fixed(180.dp),
        state = pagerState,
        contentPadding = PaddingValues(start = 0.dp, end = 80.dp),
        pageSpacing = 16.dp,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val radio = radios?.getOrNull(page) ?: return@HorizontalPager
        val request = remember(radio.image) {
            ImageRequest.Builder(context)
                .data(radio.image)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .precision(Precision.EXACT)
                .crossfade(false)
                .memoryCacheKey("${radio.image}_150")
                .placeholderMemoryCacheKey("${radio.image}_150")
                .size(150)
                .build()
        }
        Card(
            modifier = Modifier
                .size(180.dp, 220.dp)
        ) {
            Box(contentAlignment = Alignment.BottomStart) {
                AsyncImage(
                    model = request,
                    contentDescription = "描述文字",
                    modifier = Modifier
                        .fillMaxWidth()
                        .blur(8.dp),
                    contentScale = ContentScale.Crop // 裁剪模式
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color(0X19000000)),
                    // 关键：将内容推向底部
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Now",
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(16.dp, 0.dp)
                    )
                    Text(
                        radio.name, fontSize = 12.sp,
                        lineHeight = 12.sp,
                        modifier = Modifier.padding(16.dp, 0.dp)
                    )
                }
                IconButton(onClick = {
                    viewModel.fetchRadioById(radio.radioId.toString())
                }) {

                    Icon(
                        imageVector = ImageVector.vectorResource(id = yz.l.compose.home.impl.R.drawable.ic_play), // 假设存为 xml
                        contentDescription = "play",
                        tint = Color.Unspecified, // 保持原始白色系颜色
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
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