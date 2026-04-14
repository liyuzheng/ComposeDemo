package yz.l.compose.game.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import yz.l.compose.game.api.GameNavKey
import yz.l.compose.game.impl.GameScreen

/**
 * desc:
 * created by liyuzheng on 2026/4/7 13:55
 */
fun EntryProviderScope<NavKey>.gameScreenEntry() {
    entry<GameNavKey> { _ ->
        GameScreen()
    }
}