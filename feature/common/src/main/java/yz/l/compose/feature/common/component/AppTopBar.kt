package yz.l.compose.feature.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yz.l.compose.feature.common.R

/**
 * desc:
 * created by liyuzheng on 2026/4/25 16:44
 */
@Composable
fun AppTopBar(title: String, shouldHideTopBar: Boolean = false) {
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
            .let { mod ->
                if (shouldHideTopBar) {
                    // 隐藏：透明度 0，不可交互
                    mod.alpha(0f)
                } else {
                    mod.alpha(1f)
                }
            }
    ) {
        Row {
            Column(
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = title,
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