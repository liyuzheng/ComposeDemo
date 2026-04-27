package yz.l.core_tool.ext

import kotlinx.serialization.json.Json
import timber.log.Timber

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 20:40
 */
val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
    coerceInputValues = true
}

inline fun <reified T> String?.toObject(): T? {
    this ?: return null
    Timber.v(" String?.toObject $this")
    return json.decodeFromString(this)
}

fun Any.toJson(): String = json.encodeToString(this.toString())

