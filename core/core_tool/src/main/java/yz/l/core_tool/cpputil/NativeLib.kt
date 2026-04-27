package yz.l.core_tool.cpputil

import androidx.annotation.Keep

/**
 * desc:
 * created by liyuzheng on 2026/4/24 22:41
 */
@Keep
object NativeLib {
    init {
        System.loadLibrary("native-lib")
    }

    // 对应 C++ 中的函数名
    external fun getSecret(): String
}