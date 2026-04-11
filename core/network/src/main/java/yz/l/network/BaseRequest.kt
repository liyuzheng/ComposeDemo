package yz.l.network

import okhttp3.MediaType
import timber.log.Timber
import java.io.File

class BaseRequest {
    var api: String? = null

    var requestMode: Int = RequestMode.GET

    var file: File? = null

    private var params = mutableMapOf<String, Any?>()

    var contentType: MediaType? = null

    fun api(init: () -> String) {
        api = init()
    }

    fun requestMode(init: () -> Int) {
        requestMode = init()
    }

    fun file(init: () -> File) {
        file = init()
    }

    fun params(init: () -> Pair<String, Any?>) {
        val p = init()
        if(params.contains(p.first)){
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

    fun reflectParameters(): MutableMap<String, Any?> {
        return params
    }

}