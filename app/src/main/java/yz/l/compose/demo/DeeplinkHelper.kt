package yz.l.compose.demo

import android.content.Intent
import android.net.Uri
import timber.log.Timber
import yz.l.compose.feature.common.events.Bus
import yz.l.compose.feature.common.events.Event

/**
 * desc:
 * created by liyuzheng on 2026/4/24 19:14
 */
object DeeplinkHelper {
    fun dispatcher(intent: Intent) {
        val data = intent.data ?: return
        when (data) {
            is Uri -> {
                uriParser(data)
            }
        }
    }

    fun uriParser(uri: Uri) {
        val scheme = uri.scheme
        Timber.v("scheme $scheme")
        when (scheme) {
            "yuzheng" -> {
                loginParser(uri)
            }
        }
    }

    fun loginParser(uri: Uri) {
        val host = uri.host
        Timber.v("host $host")
        when (host) {
            "auth_success" -> {
                val token = uri.getQueryParameter("code").toString()
                val state = uri.getQueryParameter("state").toString()
                Bus.emit(Event.LoginEvent(token, state, "DeeplinkHelper.loginParser"))
            }
        }
    }
}