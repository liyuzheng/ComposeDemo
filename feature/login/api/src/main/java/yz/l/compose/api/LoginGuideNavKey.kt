package yz.l.compose.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yz.l.core_router.Navigator

/**
 * desc:
 * created by liyuzheng on 2026/3/25 21:08
 */
@Serializable
data object LoginGuideNavKey : NavKey

fun Navigator.navigateLoginGuide() {
    navigate(LoginGuideNavKey)
}
