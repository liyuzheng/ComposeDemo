package yz.l.compose.feature.common.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp

/**
 * desc:
 * created by liyuzheng on 2026/4/8 13:26
 */
@Composable
fun AppPullToRefreshBox(
    content: @Composable () -> Unit,
    onRefresh: suspend () -> Unit,
    isRefreshing: Boolean
) {
    val refreshThreshold = 200f // 触发刷新的阈值
    val maxDragOffset = 500f    // 最大下拉限制

    var offsetY by remember { mutableStateOf(0f) }

    // 使用 animateFloatAsState 确保松手后有平滑回弹效果
    val animatedOffset by animateFloatAsState(
        targetValue = if (isRefreshing) refreshThreshold else offsetY,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "offset"
    )

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            // 核心 1：处理向上滑动（收起 Header）
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return if (available.y < 0 && offsetY > 0) {
                    val consumed = if (offsetY + available.y > 0) available.y else -offsetY
                    offsetY += consumed
                    Offset(0f, consumed) // 拦截，不让列表滚动，先收起偏移
                } else Offset.Zero
            }

            // 核心 2：处理向下拉动（列表已到顶，开始产生跟随位移）
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                return if (available.y > 0 && source == NestedScrollSource.Drag) {
                    // 计算带阻尼的新偏移
                    val newOffset = (offsetY + available.y * 0.5f).coerceAtMost(maxDragOffset)
                    offsetY = newOffset
                    Offset(0f, available.y) // 拦截位移，反馈给 UI
                } else Offset.Zero
            }

            // 核心 3：松手瞬间检测
            override suspend fun onPreFling(available: Velocity): Velocity {
                if (offsetY >= refreshThreshold && !isRefreshing) {
                    onRefresh()
                }
                offsetY = 0f // 重置后，由 animatedOffset 接管刷新停留或回弹动画
                return super.onPreFling(available)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .background(Color.LightGray) // 方便观察背景
    ) {
        // --- 下拉刷新 Header ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .graphicsLayer {
                    // Header 始终处于列表上方：动画位移 - 自己高度
                    translationY = animatedOffset - size.height
                },
            contentAlignment = Alignment.Center
        ) {
            if (isRefreshing) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text(if (offsetY >= refreshThreshold) "松开刷新" else "继续下拉")
            }
        }

        content()
    }
}

