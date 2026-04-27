package yz.l.core_tool.utils.token

import android.util.Base64
import com.tencent.mmkv.MMKV
import timber.log.Timber
import yz.l.core_tool.MMKVReadWrite
import yz.l.core_tool.utils.CommonMMKV
import java.security.SecureRandom

/**
 * desc:
 * created by liyuzheng on 2026/4/25 13:59
 */
object TokenMMKV {
    private val mmkv by lazy {
        val mmapId = getOrCreateMMapId()
        Timber.v("TokenMMKV mmapId $mmapId")
        MMKV.mmkvWithID(
            "TokenMMKV",
            MMKV.SINGLE_PROCESS_MODE,
            mmapId
        )
    }

    var accessToken by TokenMMKV("TokenMMKV.accessToken", "")
    var refreshToken by TokenMMKV("TokenMMKV.refreshToken", "")
    var expiresIn by TokenMMKV("TokenMMKV.expiresIn", 0L)

    private fun getOrCreateMMapId(): String {
        val mmapId = CommonMMKV.mmapId
        if (mmapId.isNotBlank()) {
            return mmapId
        }
        CommonMMKV.mmapId = generateRandom16ByteKey()
        return CommonMMKV.mmapId
    }

    private fun generateRandom16ByteKey(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    class TokenMMKV<T>(key: String, defaultValue: T) : MMKVReadWrite<T>(key, defaultValue) {
        override fun getMMKV(): MMKV = mmkv
    }
}