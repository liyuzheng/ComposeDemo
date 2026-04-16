package yz.l.compose.game.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

/**
 * desc:
 * created by liyuzheng on 2026/4/11 13:20
 */
@Composable
fun GameScreen() {
    Scaffold(
        modifier = Modifier,
        topBar = {
            AppTopBar()
        },
        content = { innerPadding ->
            val pagerState = rememberPagerState(pageCount = { 10 })
            Box(modifier = Modifier.padding(innerPadding)) {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(24.dp, 0.dp, 64.dp, 0.dp),
                    // 2. 设置 Item 之间的间距
                    pageSpacing = 16.dp,
                ) { page ->
                    // 每个页面的 Composable 内容
                    Card(
                        modifier = Modifier
                            .graphicsLayer {
                                // 计算当前页面的偏移比例
                                val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                        ).absoluteValue

                                // 根据偏移量设置缩放（0.85 到 1.0 之间）
                                lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }

                                // 设置透明度
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                            .fillMaxWidth()
                            .height(200.dp)) {

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
                    text = "探索11",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
            Column(Modifier) {
//                Icon(
//                    imageVector = ImageVector.vectorResource(id = yz.l.compose.discover.impl.R.drawable.bg_discover_search), // 假设存为 xml
//                    contentDescription = "Search",
//                    tint = Color.Unspecified, // 保持原始白色系颜色
//                    modifier = Modifier.size(36.dp)
//                )
            }
        }
    }
}