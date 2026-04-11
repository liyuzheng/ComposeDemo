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
    var next: String? = null
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
    var title: String

    @SerialName("sub_title")
    val subTitle: String
}

@Serializable
data class BigDiscoverMultipleAppsCard(
    @SerialName("bg_url")
    val bgUrl: String = "",
    val desc: String = "",
    val apps: MutableList<DiscoverAppDetail> = mutableListOf(),
    override var title: String = "",
    @SerialName("sub_title")
    override val subTitle: String = ""
) : DiscoverCard

@Serializable
data class SmallDiscoverCard(
    @SerialName("bg_url")
    val bgUrl: String = "",
    val desc: String = "",
    val app: DiscoverAppDetail? = null,
    override var title: String = "",
    @SerialName("sub_title")
    override val subTitle: String = ""
) : DiscoverCard

@Serializable
data class RecommendDiscoverCard(
    @SerialName("sub_title")
    override val subTitle: String = "",
    override var title: String = "",
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
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JSON")
        val root = input.decodeJsonElement().jsonObject
        val cardType = root["card_type"]?.jsonPrimitive?.int ?: 0
        val cardId = root["id"]?.jsonPrimitive?.int ?: 0
        val cardElement = root["card"] ?: JsonNull
        val strategy = when (cardType) {
            1 -> BigDiscoverMultipleAppsCard.serializer()
            2 -> SmallDiscoverCard.serializer()
            3 -> RecommendDiscoverCard.serializer()
            else -> throw SerializationException("Unknown card_type: $cardType")
        }
        val card = input.json.decodeFromJsonElement(strategy, cardElement)
        Timber.v("card is $cardId $card")

        return DiscoverCardDetail(cardType, card, cardId)
    }

    override fun serialize(encoder: Encoder, value: DiscoverCardDetail) {
        val output = encoder as? JsonEncoder ?: throw SerializationException("Expected JSON")
        val json = buildJsonObject {
            put("card_type", value.cardType)
            put("card", output.json.encodeToJsonElement(DiscoverCard.serializer(), value.card))
            put("id", value.id)
        }
        output.encodeJsonElement(json)
    }
}