package yz.l.compose.discover.impl

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import timber.log.Timber
import yz.l.compose.discover.impl.viewmodels.DiscoverScreenViewModel
import yz.l.compose.feature.common.room.entity.BigDiscoverCardEntity
import yz.l.compose.feature.common.room.entity.DiscoverAppDetailEntity
import yz.l.compose.feature.common.room.entity.RecommendCardEntity
import yz.l.compose.feature.common.room.entity.SmallDiscoverCardEntity

/**
 * desc:
 * created by liyuzheng on 2026/4/7 13:54
 */
@Composable
@Preview
fun DiscoverScreen(
    viewModel: DiscoverScreenViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.padding(horizontal = 24.dp),
        topBar = {
            AppTopBar()
        },
        content = { innerPadding ->
            val pagingItems = viewModel.discoverCardFlow.collectAsLazyPagingItems()
            val isRefreshing = pagingItems.loadState.refresh is LoadState.Loading
            Timber.v("load ${pagingItems.itemCount} $isRefreshing")

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { pagingItems.refresh() },
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        0.dp,
                        innerPadding.calculateTopPadding(),
                        0.dp,
                        100.dp
                    ),
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(24.dp) // 设置卡片间隔
                ) {
                    // 3. 渲染数据列表
                    items(
                        count = pagingItems.itemCount,
                        key = pagingItems.itemKey { it.id }, // 必须加 key，优化性能
                        contentType = { index ->
                            val item = pagingItems[index]
                            item?.cardType ?: 0
                        }
                    ) { index ->
                        val item = pagingItems[index]
                        Timber.v("items type  ${item?.id}=${item?.cardType}")
                        when (item?.cardType) {
                            1 -> BigCard(card = item.detail as BigDiscoverCardEntity)
                            2 -> SmallCard(card = item.detail as SmallDiscoverCardEntity)
                            3 -> RecommendCard(card = item.detail as RecommendCardEntity)
                        }
                    }
                    Timber.v("appendState state is ${pagingItems.loadState.append} ")
                    when (val appendState = pagingItems.loadState.append) {
                        is LoadState.Loading -> {
                            Timber.v("appendState Loading ")
                            item {
                                // 加载中的 UI
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                }
                            }
                        }

                        is LoadState.Error -> {
                            Timber.v("appendState Error ${pagingItems.loadState.append} ")

                            item {
                                // 加载失败的 UI，通常带有一个“重试”按钮
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("加载失败")
                                    Button(onClick = { pagingItems.retry() }) {
                                        Text("点击重试")
                                    }
                                }
                            }
                        }

                        is LoadState.NotLoading -> {
                            Timber.v("appendState NotLoading ${appendState.endOfPaginationReached} ")
                            // 如果已全部加载完毕（endOfPaginationReached）
                            if (appendState.endOfPaginationReached) {
                                item {
                                    Text(
                                        text = "没有更多内容了",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        textAlign = TextAlign.Center,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        })
}

@Composable
@Preview
fun AppTopBar() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(0.dp, 24.dp, 0.dp, 12.dp)
            .statusBarsPadding()
    ) {
        Row {
            Column(
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "探索", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
            }
            Column(Modifier) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.bg_discover_search), // 假设存为 xml
                    contentDescription = "Search",
                    tint = Color.Unspecified, // 保持原始白色系颜色
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@Composable
fun RecommendCard(modifier: Modifier = Modifier, card: RecommendCardEntity) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(Modifier.size(24.dp))
            Text(card.subTitle, fontSize = 16.sp, modifier = Modifier.padding(16.dp, 0.dp))
            Text(
                card.title,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            card.apps.forEach { app ->
                RecommendCardItem(app)
            }
        }
    }
}

@Composable
fun RecommendCardItem(app: DiscoverAppDetailEntity) {
    Row(Modifier.padding(16.dp)) {
        AsyncImage(
            model = app.iconUrl, // 图片地址（可以是 URL, File, Resource 等）
            contentDescription = "描述文字",
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop, // 裁剪模式
        )
        Column(
            Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                app.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Text(
                app.desc,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
        }
        Button({}, modifier = Modifier.align(Alignment.Bottom)) {
            Text("安装")
        }
    }
}

@Composable
fun SmallCard(modifier: Modifier = Modifier, card: SmallDiscoverCardEntity) {
    val context = LocalContext.current
    val gifLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) add(ImageDecoderDecoder.Factory()) else add(
                    GifDecoder.Factory()
                )
            }
            .build()
    }
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Box {
            AsyncImage(
                model = card.bgUrl, // 图片地址（可以是 URL, File, Resource 等）
                contentDescription = "描述文字",
                modifier = Modifier
                    .fillMaxSize(),
                imageLoader = gifLoader,
                contentScale = ContentScale.Crop // 裁剪模式
            )
        }
    }
}

@Composable
fun BigCard(modifier: Modifier = Modifier, card: BigDiscoverCardEntity) {
    val context = LocalContext.current
    val gifLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) add(ImageDecoderDecoder.Factory()) else add(
                    GifDecoder.Factory()
                )
            }
            .build()
    }

    val request = remember(card.bgUrl) { // 💡 只有 URL 变了才重新构建 Request
        ImageRequest.Builder(context)
            .data(card.bgUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .precision(Precision.EXACT)
            .crossfade(false)
            .memoryCacheKey("${card.bgUrl}_400")
            .placeholderMemoryCacheKey("${card.bgUrl}_400")
            .size(400)
            .build()
    }
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.9f / 1f)
    ) {
        Box {
            AsyncImage(
                model = request,
                contentDescription = "描述文字",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.9f / 1f),
                imageLoader = gifLoader,
                contentScale = ContentScale.Crop // 裁剪模式
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                // 关键：将内容推向底部
                verticalArrangement = Arrangement.Bottom
            ) {
                CardDesc(card.subTitle, card.title, card.desc)
                Row(
                    Modifier
                        .background(Color(0X19000000))
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {
                        AsyncImage(
                            model = "https://img1.baidu.com/it/u=4099671968,3977875320&fm=253&app=138&f=JPEG?w=800&h=1067", // 图片地址（可以是 URL, File, Resource 等）
                            contentDescription = "描述文字",
                            modifier = Modifier
                                .size(64.dp, 64.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop // 裁剪模式
                        )
                    }
                    Column(
                        Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {
                        AsyncImage(
                            model = "https://img1.baidu.com/it/u=4099671968,3977875320&fm=253&app=138&f=JPEG?w=800&h=1067", // 图片地址（可以是 URL, File, Resource 等）
                            contentDescription = "描述文字",
                            modifier = Modifier
                                .size(64.dp, 64.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop // 裁剪模式
                        )
                    }
                    Column(
                        Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {
                        AsyncImage(
                            model = "https://img1.baidu.com/it/u=4099671968,3977875320&fm=253&app=138&f=JPEG?w=800&h=1067", // 图片地址（可以是 URL, File, Resource 等）
                            contentDescription = "描述文字",
                            modifier = Modifier
                                .size(64.dp, 64.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop // 裁剪模式
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardDesc(subTitle: String, title: String, desc: String) {
    Text(subTitle, fontSize = 16.sp, modifier = Modifier.padding(16.dp, 0.dp))
    Text(
        title,
        fontSize = 36.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(16.dp, 0.dp)
    )
    Text(
        desc,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
}