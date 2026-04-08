package yz.l.compose.home.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * desc:
 * created by liyuzheng on 2026/4/6 19:47
 */

@Composable
fun BottomNavigationBar(onClick: (Int) -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(20.dp, 0.dp, 20.dp, 32.dp)
    ) {
        BGNavigationBar(
            Modifier
                .align(Alignment.Center)
        )
        Row(
            Modifier
                .align(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Button({
                    onClick(1)
                }, modifier = Modifier.background(Color.Red)) { }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Button({
                    onClick(2)
                }, modifier = Modifier.background(Color.Red)) { }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Button({
                    onClick(3)
                }, modifier = Modifier.background(Color.Red)) { }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Button({
                    onClick(4)
                }, modifier = Modifier.background(Color.Red)) { }
            }
        }

    }
}

@Composable
@Preview
fun BGNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(2.dp, shape = RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(24.dp))
            .blur(8.dp)                     // 产生毛玻璃模糊
            .background(Color.Transparent, shape = RoundedCornerShape(24.dp))
            .border(1.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}