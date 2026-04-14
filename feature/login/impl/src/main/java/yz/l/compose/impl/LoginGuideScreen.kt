package yz.l.compose.impl

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yz.l.compose.api.LotteryNavKey
import yz.l.compose.feature.common.component.SystemStatusBar
import yz.l.compose.login.impl.R
import yz.l.core_router.NavigatorService

/**
 * desc:
 * created by liyuzheng on 2026/3/25 21:11
 */
@Composable
fun LoginGuideScreen(navigator: NavigatorService) {
    LoginGuideScreen(navigator) {

    }
}

@Composable
fun LoginGuideScreen(
    navigator: NavigatorService,
    onClick: () -> Unit
) {
    SystemStatusBar(false)
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        content = { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AutoMarqueeImage(R.drawable.bg_login_guide)
                BgGuide()

                Button(
                    modifier = Modifier
                        .size(320.dp, 55.dp)
                        .align(Alignment.BottomCenter)
                        .offset(0.dp, (-80).dp),
                    onClick = {
                        navigator.replace(LotteryNavKey(""))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        fontSize = 18.sp,
                        text = "手机号登录",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}

@Composable
fun BoxWithConstraintsScope.BgGuide() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(514.dp)
            .align(Alignment.BottomCenter)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x00221b18),
                        Color(0xFF2c2522),
                        Color(0xFF29221f)
                    ),
                    0f,
                    Float.POSITIVE_INFINITY
                )
            ),
    )
}

@Composable
fun AutoMarqueeImage(
    @DrawableRes drawableId: Int,
    duration: Int = 500000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val targetValue = 10000f
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
            },
            repeatMode = RepeatMode.Restart
        )
    )
    val img = ImageBitmap.imageResource(drawableId)
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val imageWidth = img.width.toFloat()
            val imageHeight = img.height.toFloat()
            val scale = width / imageWidth
            val scaledHeight = imageHeight * scale * 1.5
            val scaledWidth = width * 1.5
            val y =
                offset / targetValue * scaledHeight - scaledHeight + height
            val y1 =
                -scaledHeight + (offset / targetValue * scaledHeight) - scaledHeight + height
            val dstSize = IntSize((scaledWidth).toInt(), (scaledHeight).toInt())
            val x = (-width * (1.5 - 1) / 2).toInt()
            img.prepareToDraw()
            rotate(-10f) {
                drawImage(
                    image = img,
                    dstSize = dstSize,
                    dstOffset = IntOffset(x, y.toInt())
                )

                drawImage(
                    image = img,
                    dstSize = dstSize,
                    dstOffset = IntOffset(x, y1.toInt())
                )
            }
        }
    }
}

@Composable
@Preview
fun LoginGuideScreenPreview() {
//    val navigator = Navigator(NavBackStack<NavKey>(), ResultStore())
//    LoginGuideScreen(navigator) {}
}