package yz.l.compose.impl

import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import timber.log.Timber
import yz.l.compose.feature.common.events.Event
import yz.l.core_router.LocalNavigator
import yz.l.core_tool.BuildConfig
import yz.l.core_tool.utils.randomString

/**
 * desc:
 * created by liyuzheng on 2026/3/16 18:51
 */

@Composable
fun JamendoAuthWebView(state: String) {
    val context = LocalContext.current
    val authUrl =
        "https://api.jamendo.com/v3.0/oauth/authorize?client_id=${BuildConfig.clientId}&state=$state&prompt=login"
    val customTabsIntent = CustomTabsIntent.Builder()
        .setShowTitle(false) // 显示网页标题
        .build()
    customTabsIntent.intent.putExtra("com.google.android.apps.chrome.EXTRA_IS_INCOGNITO", true)
    Button(onClick = {
        customTabsIntent.launchUrl(context, authUrl.toUri())
    }) {
        Text("去 Jamendo 授权")
    }
}


@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val navigator = LocalNavigator.current
    val randomState = remember { randomString(10) }
    JamendoAuthWebView(randomState)
    val event by viewModel.eventFlow.collectAsStateWithLifecycle(null)
    Timber.v("LoginScreen $randomState")
    LaunchedEffect(event) {
        if (event != null && event is Event.LoginEvent) {
            Timber.v("LoginScreen.LaunchedEffect $event")
            val code = (event as Event.LoginEvent).refreshToken
            val state = (event as Event.LoginEvent).state
            Timber.v("loginEvent.LaunchedEffect $code $state $randomState")
            viewModel.requestToken(code)
        }
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

