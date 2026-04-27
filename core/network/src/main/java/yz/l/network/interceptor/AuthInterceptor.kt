package yz.l.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import yz.l.core_tool.BuildConfig
import yz.l.network.NetworkExceptionConstantCode
import yz.l.network.token.TokenManager

/**
 * desc:
 * created by liyuzheng on 2026/4/24 23:25
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.method.lowercase() == "post")
            return chain.proceed(originalRequest)
        val originalUrl = originalRequest.url
        val needAccessToken =
            originalRequest.header(NetworkExceptionConstantCode.NEED_ACCESS_TOKEN) == true.toString()
        var urlBuilder =
            originalUrl.newBuilder().addQueryParameter("client_id", BuildConfig.clientId)
        if (needAccessToken) {
            urlBuilder =
                urlBuilder.addQueryParameter("access_token", TokenManager.getAccessToken())
        }

        val newUrl = urlBuilder.build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .removeHeader(NetworkExceptionConstantCode.NEED_ACCESS_TOKEN)
            .build()

        val response = chain.proceed(newRequest)

        return response
    }
}