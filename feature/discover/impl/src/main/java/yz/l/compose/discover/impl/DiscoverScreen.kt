package yz.l.compose.discover.impl

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import timber.log.Timber
import yz.l.compose.api.LoginNavKey
import yz.l.compose.discover.data.BigDiscoverMultipleAppsCard
import yz.l.compose.discover.data.DiscoverAppDetail
import yz.l.compose.discover.data.RecommendDiscoverCard
import yz.l.compose.discover.data.SmallDiscoverCard
import yz.l.compose.discover.impl.viewmodels.DiscoverScreenViewModel
import yz.l.compose.feature.common.component.refreshlayout.PagingRefreshLayout
import yz.l.core_router.LocalNavigator

/**
 * desc:
 * created by liyuzheng on 2026/4/7 13:54
 */
@Composable
@Preview
fun DiscoverScreen(
    viewModel: DiscoverScreenViewModel = hiltViewModel()
) {
    Timber.v("DiscoverScreen compose")
    Scaffold(
        topBar = {
            AppTopBar()
        },
        content = { innerPadding ->
            val navigator = LocalNavigator.current
            val pagingItems = viewModel.pagingItems.collectAsLazyPagingItems()
            PagingRefreshLayout(
                modifier = Modifier.padding(horizontal = 24.dp),
                innerPadding = innerPadding,
                pagingItems = pagingItems
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.id }, // 必须加 key，优化性能
                    contentType = { index ->
                        pagingItems.itemContentType {
                            val item = pagingItems[index]
                            item?.cardType ?: 0
                        }
                    }
                ) { index ->
                    val item = pagingItems[index]
                    if (item != null) {
                        when (item.cardType) {
                            1 -> BigCard(
                                id = item.id,
                                card = item.card as BigDiscoverMultipleAppsCard
                            )

                            2 -> SmallCard(id = item.id, card = item.card as SmallDiscoverCard)
                            3 -> RecommendCard(
                                id = item.id,
                                card = item.card as RecommendDiscoverCard
                            ) {
                            }
                        }
                    }
                }
            }
            Button(onClick = {
                navigator.navigate(LoginNavKey())
            }, modifier = Modifier.padding(innerPadding)) {
                Text("模拟搜111索")
            }
        })
}

@Composable
@Preview
fun AppTopBar() {
    Box(
        Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 1f),
                        Color.Transparent
                    ),
                    start = Offset(x = 0f, y = 0f),    // 顶部开始
                    end = Offset(x = 0f, y = Float.POSITIVE_INFINITY) // 正下方结束
                )
            )
            .padding(24.dp, 0.dp, 24.dp, 12.dp)
            .statusBarsPadding()

    ) {
        Row {
            Column(
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "探索222333",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
            Column(Modifier) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.bg_discover_search), // 假设存为 xml
                    contentDescription = "Search",
                    tint = Color.Unspecified, // 保持原始白色系颜色
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
fun RecommendCard(
    id: Int,
    modifier: Modifier = Modifier,
    card: RecommendDiscoverCard,
    onClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(Modifier.size(24.dp))
            Text(card.subTitle, fontSize = 12.sp, modifier = Modifier.padding(16.dp, 0.dp))
            Text(
                card.title + id.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            card.apps.forEach { app ->
                RecommendCardItem(id, app, onClick)
            }
        }
    }
}

@Composable
fun RecommendCardItem(id: Int, app: DiscoverAppDetail, onClick: (String) -> Unit) {
    Row(Modifier.padding(16.dp)) {
        AsyncImage(
            model = app.iconUrl, // 图片地址（可以是 URL, File, Resource 等）
            contentDescription = "描述文字",
            onState = { state ->
                if (state is AsyncImagePainter.State.Error) {
                    val throwable = state.result.throwable
                    // 打印详细错误日志
                    Timber.e("图片加载失败1: $id  ${app.iconUrl} $throwable")
                }
            },
            modifier = Modifier
                .size(48.dp)
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
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Text(
                app.desc,
                fontSize = 12.sp,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
        }
        Button(
            {
                onClick(app.name)
            }, modifier = Modifier
                .align(Alignment.Bottom)
                .size(80.dp, 32.dp)
        ) {
            Text("安装", fontSize = 12.sp, textAlign = TextAlign.Center, maxLines = 1)
        }
    }
}

@Composable
fun SmallCard(id: Int, modifier: Modifier = Modifier, card: SmallDiscoverCard) {
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
            .height(120.dp)
    ) {
        Box {
            AsyncImage(
                model = card.bgUrl, // 图片地址（可以是 URL, File, Resource 等）
                contentDescription = "描述文字",
                modifier = Modifier
                    .fillMaxSize(),
                onState = { state ->
                    if (state is AsyncImagePainter.State.Error) {
                        val throwable = state.result.throwable
                        // 打印详细错误日志
                        Timber.e("图片加载失败2: $id  ${card.bgUrl} $throwable")
                    }
                },
                imageLoader = gifLoader,
                contentScale = ContentScale.Crop // 裁剪模式
            )
            Text(
                id.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
        }
    }
}

@Composable
fun BigCard(id: Int, modifier: Modifier = Modifier, card: BigDiscoverMultipleAppsCard) {
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
                CardDesc(card.subTitle, card.title + id.toString(), card.desc)
                if (card.apps.isEmpty()) return@Box
                Row(
                    Modifier
                        .background(Color(0X19000000))
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    card.apps.forEach { app ->
                        Column(
                            Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        ) {
                            AsyncImage(
                                model = app.iconUrl, // 图片地址（可以是 URL, File, Resource 等）
                                contentDescription = "描述文字",
                                onState = { state ->
                                    if (state is AsyncImagePainter.State.Error) {
                                        val throwable = state.result.throwable
                                        // 打印详细错误日志
                                        Timber.e("图片加载失败: $id  ${card.bgUrl} $throwable")
                                    }
                                },
                                modifier = Modifier
                                    .size(36.dp, 36.dp)
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
}

@Composable
fun CardDesc(subTitle: String, title: String, desc: String) {
    Text(
        subTitle, fontSize = 12.sp,
        lineHeight = 12.sp,
        modifier = Modifier.padding(16.dp, 0.dp)
    )
    Text(
        title,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(16.dp, 0.dp)
    )
    Text(
        desc,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        modifier = Modifier.padding(start = 16.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
}