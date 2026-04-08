package yz.l.compose.feature.common.room.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * desc:
 * created by liyuzheng on 2026/4/7 17:12
 */
@Keep
@Entity(tableName = "discover_cards")
data class DiscoverCardTable(
    @PrimaryKey
    val id: Long = 0,
    val cardType: Int,
    val detail: DiscoverCardDetailEntity,
    val remoteName: String
)

@Serializable
sealed interface DiscoverCardDetailEntity { // 使用密封接口代替 open class
    val title: String
    val subTitle: String
}

@Serializable
@SerialName("BIG") // 序列化时标记为大卡片
data class BigDiscoverCardEntity(
    override val title: String = "",
    override val subTitle: String = "",
    val bgUrl: String,
    val desc: String,
    val apps: List<DiscoverAppDetailEntity> = listOf()
) : DiscoverCardDetailEntity

@Serializable
@SerialName("SMALL") // 序列化时标记为小卡片
data class SmallDiscoverCardEntity(
    override val title: String = "",
    override val subTitle: String = "",
    val bgUrl: String,
    val desc: String,
    val app: DiscoverAppDetailEntity?
) : DiscoverCardDetailEntity

@Serializable
@SerialName("APP_DETAIL")
data class DiscoverAppDetailEntity(
    val iconUrl: String,
    val name: String,
    val desc: String,
    val deeplink: String
)

@Serializable
@SerialName("RECOMMEND")
data class RecommendCardEntity(
    override val title: String = "",
    override val subTitle: String = "",
    val apps: List<DiscoverAppDetailEntity> = listOf()
) : DiscoverCardDetailEntity