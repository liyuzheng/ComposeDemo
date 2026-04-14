package yz.l.compose.game.impl.provider

import androidx.compose.runtime.Composable
import yz.l.compose.game.api.GameApi
import yz.l.compose.game.impl.GameScreen
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/4/11 13:21
 */
class GameApiProvider @Inject constructor() : GameApi {
    @Composable
    override fun GetGameScreen() {
        GameScreen()
    }
}