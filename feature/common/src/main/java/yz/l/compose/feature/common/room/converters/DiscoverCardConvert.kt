package yz.l.compose.feature.common.room.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import yz.l.compose.feature.common.room.entity.DiscoverCardDetailEntity

/**
 * desc:
 * created by liyuzheng on 2026/4/7 17:29
 */
open class DiscoverCardConvert {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @TypeConverter
    fun fromDiscoverDetail(value: DiscoverCardDetailEntity): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toDiscoverDetail(value: String): DiscoverCardDetailEntity {
        return json.decodeFromString(value)
    }
}