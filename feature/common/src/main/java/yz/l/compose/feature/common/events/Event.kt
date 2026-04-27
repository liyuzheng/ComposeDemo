package yz.l.compose.feature.common.events

/**
 * desc:
 * created by liyuzheng on 2026/4/24 19:31
 */
sealed class Event(open val from: String) {
    data class LoginEvent(val refreshToken: String, val state: String, override val from: String) :
        Event(from)
}