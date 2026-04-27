package yz.l.network

import okhttp3.MediaType
import timber.log.Timber
import java.io.File

class BaseRequest {
    var api: String? = null

    var requestMode: Int = RequestMode.GET

    var file: File? = null

    private var params = mutableMapOf<String, String>()

    var contentType: MediaType? = null

    var needAccessToken: Boolean = true

    fun api(value: String) {
        api = value
    }

    fun requestMode(value: Int) {
        requestMode = value
    }

    fun file(init: () -> File) {
        file = init()
    }

    fun params(init: () -> Pair<String, String>) {
        val p = init()
        if (params.contains(p.first)) {
            Timber.e("BaseRequest params 重复key")
            throw IllegalArgumentException("params 重复key")
        }
        params[p.first] = p.second
    }

    fun contentType(init: () -> MediaType?) {
        contentType = init()
    }

    override fun toString(): String {
        return "api:$api \n params :$params"
    }

    fun needAccessToken(value: Boolean) {
        needAccessToken = value
    }

    fun reflectParameters(): MutableMap<String, String> {
        return params
    }

}