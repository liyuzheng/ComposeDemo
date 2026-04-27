package yz.l.network

//import yz.l.core_tool.ext.debug
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import yz.l.core_tool.ext.debug
import yz.l.network.interceptor.AuthInterceptor
import java.util.concurrent.TimeUnit

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 19:48
 */
class RetrofitClient : IRetrofitClient {
    companion object {
        private const val BASE_URL = "https://www.baidu.com"
    }

    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        isLenient = true
        coerceInputValues = true
    }
    val contentType = "application/json".toMediaType()

    private fun provideOkHttpClient(): OkHttpClient {
        var builder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(AuthInterceptor())
        debug {
            builder = builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }

    override fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(StringConverterFactory())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}