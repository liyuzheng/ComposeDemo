package yz.l.core_router.strategy

/**
 * desc:
 * created by liyuzheng on 2026/4/4 19:47
 */
object NavigateModeStrategyContext {
    const val DEFAULT = 0
    const val SINGLE_TOP = 1
    const val CLEAR_TASK = 2
    const val REPLACE = 3
    fun createStrategy(key: Int): NavigateModeStrategy {
        return when (key) {
            SINGLE_TOP -> SingleTopNavigateStrategy()
            CLEAR_TASK -> ClearTaskNavigateStrategy()
            REPLACE -> ReplaceNavigateStrategy()
            else -> DefaultNavigateStrategy()
        }
    }
}