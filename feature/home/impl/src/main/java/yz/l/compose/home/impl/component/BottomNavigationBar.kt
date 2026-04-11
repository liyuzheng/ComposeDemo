package yz.l.compose.home.impl.component

import android.os.Build
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.abs

/**
 * desc:
 * created by liyuzheng on 2026/4/6 19:47
 */

@Composable
fun LightFlowLottieNavBar(
    modifier: Modifier = Modifier,
    items: List<NavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    // 整个导航栏的容器（浮动圆角矩形）
    Box(
        modifier = modifier
            .padding(24.dp, 0.dp, 32.dp, 24.dp)
            .fillMaxWidth()
            .height(32.dp)
            .shadow(2.dp, shape = RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(24.dp))
            .background(Color.Transparent, shape = RoundedCornerShape(24.dp))
            .border(1.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(24.dp))
    ) {
        // --- 层级 1：毛玻璃背景 ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(25.dp))
                .then(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Modifier.blur(15.dp) // API 31+ 真正的模糊
                    } else Modifier
                )
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.12f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    )
                )
                .border(
                    width = 0.5.dp,
                    brush = Brush.verticalGradient(
                        listOf(Color.White.copy(alpha = 0.2f), Color.Transparent)
                    ),
                    shape = RoundedCornerShape(25.dp)
                )
        )

        LightAnimBg(selectedIndex, items.size)
        // --- 层级 3：Lottie 图标层 ---
        Row(modifier = Modifier.fillMaxSize()) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex
                LottieNavButton(isSelected, item, index, onItemSelected)

            }
        }
    }
}

data class NavItem(
    val title: String,
    val lottieRes: Int, // 放在 assets 文件夹下的 json 文件名
    val activeColor: Color = Color.White
)

@Composable
fun LightAnimBg(
    selectedIndex: Int,
    itemSize: Int
) {
    // --- 层级 2：光传导 Canvas ---
    val density = LocalDensity.current
    val indicatorOffset = remember { Animatable(0f) }
    var stretchFactor by remember { mutableFloatStateOf(1f) }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .blur(8.dp)
    ) {
        val widthPx = with(density) { maxWidth.toPx() }
        val itemWidthPx = widthPx / itemSize
        val targetX = selectedIndex * itemWidthPx + (itemWidthPx / 2)

        LaunchedEffect(selectedIndex) {
            indicatorOffset.animateTo(
                targetX,
                spring(stiffness = Spring.StiffnessLow, dampingRatio = 0.7f)
            )
        }

        LaunchedEffect(Unit) {
            var lastValue = indicatorOffset.value
            snapshotFlow { indicatorOffset.value }.collectLatest { current ->
                stretchFactor = (1f + (abs(current - lastValue) / 20f)).coerceIn(1f, 2.2f)
                lastValue = current
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(indicatorOffset.value, size.height / 2)
            withTransform({
                scale(scaleX = stretchFactor, scaleY = 1f, pivot = center)
            }) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.45f), // 增加中心亮度
                            Color.White.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = 45.dp.toPx()
                    ),
                    center = center,
                    radius = 45.dp.toPx(),
                    blendMode = BlendMode.Screen // 屏幕混合模式让白色更通透
                )

                // 2. 核心强光点（模拟白色灯珠）
                drawCircle(
                    color = Color.White,
                    center = center,
                    radius = 3.5.dp.toPx(),
                    // 添加一点微弱的阴影/外光晕使白点更立体
                    alpha = 0.9f
                )
            }
        }
    }

}

@Composable
fun RowScope.LottieNavButton(
    isSelected: Boolean,
    item: NavItem,
    index: Int,
    onItemSelected: (Int) -> Unit
) {
    // 加载 Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(item.lottieRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isSelected,
        restartOnPlay = true
    )

    // 动态修改未选中时的颜色（兼容最新 API）
    val blackProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR, // 使用 COLOR 属性更清晰
            value = android.graphics.Color.BLACK,
            keyPath = arrayOf("**")
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR, // 使用 COLOR 属性更清晰
            value = android.graphics.Color.BLACK,
            keyPath = arrayOf("**")
        )
    )

// 2. 定义选中时的白色属性
    val whiteProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = android.graphics.Color.BLUE,
            keyPath = arrayOf("**")
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR, // 使用 COLOR 属性更清晰
            value = android.graphics.Color.BLUE,
            keyPath = arrayOf("**")
        )
    )

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onItemSelected(index) },
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            dynamicProperties = if (isSelected) whiteProperties else blackProperties,
            modifier = Modifier
                .size(24.dp)
                .graphicsLayer {
                    scaleX = if (isSelected) 1.2f else 1f
                    scaleY = if (isSelected) 1.2f else 1f
                    alpha = if (isSelected) 1f else 0.6f
                }
        )
    }
}