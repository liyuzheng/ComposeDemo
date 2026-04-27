package yz.l.core_tool.utils

/**
 * desc:
 * created by liyuzheng on 2026/4/25 13:52
 */
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object KeyStoreUtil {

    private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    private const val AES_MODE = "AES/GCM/NoPadding"
    private const val KEY_ALIAS = "MyAppSecretKey" // 密钥别名

    private val keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply {
        load(null)
    }

    /**
     * 生成或获取现有的密钥
     */
    fun getOrCreateKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        if (existingKey != null) return existingKey.secretKey

        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    /**
     * 加密字符串
     * 返回结果格式：IV + "|" + CipherText (Base64编码)
     */
    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        val iv = cipher.iv // GCM 必须记录 IV 以便解密
        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

        val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
        val encryptedString = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)

        return "$ivString|$encryptedString"
    }

    /**
     * 解密字符串
     * @param encryptedData 格式为 "IV|CipherText" 的字符串
     */
    fun decrypt(encryptedData: String): String {
        val parts = encryptedData.split("|")
        if (parts.size != 2) throw IllegalArgumentException("Invalid encrypted data format")

        val iv = Base64.decode(parts[0], Base64.DEFAULT)
        val cipherBytes = Base64.decode(parts[1], Base64.DEFAULT)

        val cipher = Cipher.getInstance(AES_MODE)
        val spec = GCMParameterSpec(128, iv) // 128位认证标签长度
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey(), spec)

        return String(cipher.doFinal(cipherBytes), Charsets.UTF_8)
    }
}
