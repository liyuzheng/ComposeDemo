package yz.l.network.token

import yz.l.core_tool.utils.token.TokenMMKV

/**
 * desc:
 * created by liyuzheng on 2026/4/24 23:27
 */
object TokenManager {
    fun getAccessToken(): String {
        val accessToken = TokenMMKV.accessToken
        return accessToken
    }

    fun getRefreshToken(): String {
        val refreshToken = TokenMMKV.refreshToken
        return refreshToken
    }

    fun save(accessToken: String, refreshToken: String, expiresIn: Long) {
        TokenMMKV.accessToken = accessToken
        TokenMMKV.refreshToken = refreshToken
        TokenMMKV.expiresIn = expiresIn
    }
}