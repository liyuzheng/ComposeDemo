package yz.l.compose.discover.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import timber.log.Timber
import yz.l.network.BaseResponse

/**
 * desc:
 * created by liyuzheng on 2026/4/7 13:58
 */
@Serializable
data class DiscoverCardModel(
    @SerialName("data")
    val cards: MutableList<DiscoverCardDetail> = mutableListOf(),
    val next: String? = null
) : BaseResponse()

@Serializable(with = DiscoverCardDetailSerializer::class)
data class DiscoverCardDetail(
    @SerialName("card_type")
    val cardType: Int,
    val card: DiscoverCard,
    val id: Int
)

@Serializable
sealed interface DiscoverCard { // 使用密封接口代替 open class
    val title: String

    @SerialName("sub_title")
    val subTitle: String
}

@Serializable
data class BigDiscoverMultipleAppsCard(
    @SerialName("bg_url")
    val bgUrl: String = "",
    val desc: String = "",
    val apps: MutableList<DiscoverAppDetail> = mutableListOf(),
    override val title: String = "",
    @SerialName("sub_title")
    override val subTitle: String = ""
) : DiscoverCard

@Serializable
data class SmallDiscoverCard(
    @SerialName("bg_url")
    val bgUrl: String = "",
    val desc: String = "",
    val app: DiscoverAppDetail? = null,
    override val title: String = "",
    @SerialName("sub_title")
    override val subTitle: String = ""
) : DiscoverCard

@Serializable
data class RecommendCard(
    @SerialName("sub_title")
    override val subTitle: String = "",
    override val title: String = "",
    val apps: MutableList<DiscoverAppDetail> = mutableListOf()
) : DiscoverCard

@Serializable
data class DiscoverAppDetail(
    @SerialName("icon_url")
    val iconUrl: String = "",
    val name: String = "",
    val desc: String = "",
    @SerialName("deeplink")
    val deeplink: String = ""
)

object DiscoverCardDetailSerializer : KSerializer<DiscoverCardDetail> {
    // 定义这个类的结构描述
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("DiscoverCardDetail") {
        element<Int>("card_type")
        element<JsonElement>("card")
        element<Int>("id")
    }

    override fun deserialize(decoder: Decoder): DiscoverCardDetail {
        Timber.v("start deserialize")
        // 将输入转为 JsonInput 才能进行逐字段解析
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JSON")
        val root = input.decodeJsonElement().jsonObject

        // 1. 拿到外部的 card_type
        val cardType = root["card_type"]?.jsonPrimitive?.int ?: 0
        val cardId = root["id"]?.jsonPrimitive?.int ?: 0
        Timber.v("card_type is $cardType")

        // 2. 拿到内部的 card JSON 内容
        val cardElement = root["card"] ?: JsonNull

        Timber.v("cardElement is $cardElement")


        // 3. 根据外部 cardType 决定使用哪个子类的序列化器
        val strategy = when (cardType) {
            1 -> BigDiscoverMultipleAppsCard.serializer()
            2 -> SmallDiscoverCard.serializer()
            3 -> RecommendCard.serializer()
            else -> throw SerializationException("Unknown card_type: $cardType")
        }
        Timber.v("strategy is $strategy")

        // 4. 手动调用对应子类的反序列化方法
        val card = input.json.decodeFromJsonElement(strategy, cardElement) as DiscoverCard
        Timber.v("card is $cardId $card")

        return DiscoverCardDetail(cardType, card, cardId)
    }

    override fun serialize(encoder: Encoder, value: DiscoverCardDetail) {
        // 序列化逻辑（如果只是读取，可以简单实现）
        val output = encoder as? JsonEncoder ?: throw SerializationException("Expected JSON")
        val json = buildJsonObject {
            put("card_type", value.cardType)
            put("card", output.json.encodeToJsonElement(DiscoverCard.serializer(), value.card))
            put("id", value.id)
        }
        output.encodeJsonElement(json)
    }
}