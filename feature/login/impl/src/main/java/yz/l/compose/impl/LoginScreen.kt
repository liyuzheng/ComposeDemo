package yz.l.compose.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:51
 */
@Composable
fun LoginScreen(
    onClick: (String) -> Unit,
) {
    val vm: LoginViewModel = hiltViewModel()
    LaunchedEffect(vm) {
        vm.test()
    }
    DisposableEffect(vm) {
        // 初始化逻辑（如注册监听器、启动观察者等）
        onDispose {
            // 清理逻辑（如移除监听器、释放资源）
        }
    }
    LoginContent {
        onClick("testResult")
    }
}

@Composable
fun LoginContent(onClick: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                icon = { },
                onClick = {
                    onClick()
                }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth()
            ) {
                InputField()
                Button(
                    onClick = {
                        onClick()
                    },
                    content = {
                        Text("Click to close")
                    }
                )
            }
        })
}

@Composable
@Preview(showBackground = true)
fun InputField() {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        TextField(
            state = rememberTextFieldState("用户名"),
            lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = 1),
            placeholder = { Text("") },
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp),
        )

        TextField(
            state = rememberTextFieldState("密码"),
            lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = 1),
            placeholder = { Text("") },
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
    }
}

