package yz.l.network.interceptor

import kotlinx.coroutines.sync.Mutex
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * desc:
 * created by liyuzheng on 2026/4/24 23:21
 */
class TokenAuthenticator() : Authenticator {
    val lock = Mutex()
    override fun authenticate(route: Route?, response: Response): Request? {
// 防止无限循环（最多重试一次）
        if (response.priorResponse != null) {
            return null
        }

//        synchronized(lock) {
//            // 1. 获取当前存储的Token
//            val currentAccessToken = tokenManager.getAccessToken()
//            val currentRefreshToken = tokenManager.getRefreshToken()
//
//            // 2. 如果已有新的Token，直接重试
//            if (currentAccessToken != null &&
//                currentAccessToken != response.request.header("Authorization")?.removePrefix("Bearer ")
//            ) {
//                return response.request.newBuilder()
//                    .header("Authorization", "Bearer $currentAccessToken")
//                    .build()
//            }
//
//            // 3. 执行刷新Token的网络请求
//            val refreshResponse = authApi.refreshToken("Bearer $currentRefreshToken").execute()
//
//            return if (refreshResponse.isSuccessful) {
//                val newToken = refreshResponse.body()?.accessToken ?: return null
//                // 4. 保存新Token
//                tokenManager.saveAccessToken(newToken)
//                // 5. 用新Token重试原始请求
//                response.request.newBuilder()
//                    .header("Authorization", "Bearer $newToken")
//                    .build()
//            } else {
//                // 刷新失败，清空数据，引导用户重新登录
//                tokenManager.clear()
//                null
//            }
//        }
        return null
    }
}