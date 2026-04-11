package yz.l.compose.impl

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import yz.l.compose.feature.common.component.ShowStatusBar

/**
 * desc:
 * created by liyuzheng on 2026/3/16 19:26
 */

@Composable
fun LotteryScreen(id: String, onClick: (String) -> Unit) {
    ShowStatusBar(true)
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.padding(bottom = 48.dp),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.offset(y = (-48).dp),
                text = { Text("Show snackbar") },
                icon = { },
                onClick = {

                }
            )
        },
        content = { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
            }
        })
}


@Composable
fun TestV() {
    LazyColumn {
        stickyHeader { }
    }
}

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    delay: Long = 500L,
    all: Boolean = false,
    content: @Composable RowScope.() -> Unit,
) {
    val lastClickTime = remember { mutableLongStateOf(0L) }
    Button(
        onClick = {
            val currTime = System.currentTimeMillis()
            if (currTime - lastClickTime.longValue > delay) {
                lastClickTime.longValue = currTime
                onClick()
            }
        },
        content = content
    )
}