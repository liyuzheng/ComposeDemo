package yz.l.core_tool.utils

import com.tencent.mmkv.MMKV
import yz.l.core_tool.MMKVReadWrite

/**
 * desc:
 * created by liyuzheng on 2026/4/25 14:12
 */
object CommonMMKV {
    private val mmkv by lazy {
        MMKV.mmkvWithID("CommonMMKV")
    }

    var mmapId by CommonMMKV("token.mmapId", "")

    class CommonMMKV<T>(key: String, defaultValue: T) : MMKVReadWrite<T>(key, defaultValue) {
        override fun getMMKV(): MMKV = mmkv
    }
}