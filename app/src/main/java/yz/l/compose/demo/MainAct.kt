package yz.l.compose.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import yz.l.compose.demo.themes.AppTheme

/**
 * desc:
 * created by liyuzheng on 2026/3/14 18:04
 */
class MainAct : ComponentActivity() {
    private val viewModel: MainActViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            !viewModel.isReadyObs.value
        }
        viewModel.init()
        setContent {
            AppTheme {
                AppHost()
            }
        }
//        setupExitSplashScreenAnim(splashScreen)

    }

    private fun setupExitSplashScreenAnim(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { provider ->
            val splashScreenView = provider.view
            val springAnim = SpringAnimation(
                splashScreenView, SpringAnimation.TRANSLATION_X, splashScreenView.x
            )
            springAnim.spring.stiffness = SpringForce.STIFFNESS_MEDIUM
            springAnim.setStartVelocity(-2000f)
            springAnim.setStartValue(splashScreenView.x)
            springAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            springAnim.start()

            springAnim.addEndListener { _, _, _, _ ->
                setContent {
//                    Show()
                }
                provider.remove()
            }
        }
    }
}