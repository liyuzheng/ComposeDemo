package yz.l.compose.discover.data.mapper

import yz.l.compose.discover.data.BigDiscoverMultipleAppsCard
import yz.l.compose.discover.data.DiscoverAppDetail
import yz.l.compose.discover.data.DiscoverCard
import yz.l.compose.discover.data.DiscoverCardDetail
import yz.l.compose.discover.data.RecommendCard
import yz.l.compose.discover.data.SmallDiscoverCard
import yz.l.compose.feature.common.room.entity.BigDiscoverCardEntity
import yz.l.compose.feature.common.room.entity.DiscoverAppDetailEntity
import yz.l.compose.feature.common.room.entity.DiscoverCardDetailEntity
import yz.l.compose.feature.common.room.entity.DiscoverCardTable
import yz.l.compose.feature.common.room.entity.RecommendCardEntity
import yz.l.compose.feature.common.room.entity.SmallDiscoverCardEntity
import yz.l.core_tool.ext.transform

/**
 * desc:
 * created by liyuzheng on 2026/4/7 19:40
 */
fun DiscoverCardDetail.toDiscoverCardTable(remoteName: String) = DiscoverCardTable(
    cardType = this.cardType,
    detail = this.card.toDiscoverCardDetailEntity(cardType),
    id = this.id.toLong(),
    remoteName = remoteName
)

fun DiscoverCard.toDiscoverCardDetailEntity(cardType: Int): DiscoverCardDetailEntity {
    return when (cardType) {
        1 -> (this as BigDiscoverMultipleAppsCard).toBigDiscoverCardEntity()
        2 -> (this as SmallDiscoverCard).toSmallDiscoverCardEntity()
        3 -> (this as RecommendCard).toRecommendCardEntity()
        else -> throw RuntimeException("cardType不存在$cardType")
    }
}

fun BigDiscoverMultipleAppsCard.toBigDiscoverCardEntity() = BigDiscoverCardEntity(
    title = this.title,
    subTitle = this.subTitle,
    bgUrl = this.bgUrl,
    desc = this.desc,
    apps = this.apps.map { it.toDiscoverAppDetailEntity() }
)

fun DiscoverAppDetail.toDiscoverAppDetailEntity() = DiscoverAppDetailEntity(
    name = this.name,
    desc = this.desc,
    iconUrl = this.iconUrl,
    deeplink = this.deeplink
)

fun SmallDiscoverCard.toSmallDiscoverCardEntity() = SmallDiscoverCardEntity(
    title = this.title,
    subTitle = this.subTitle,
    desc = this.desc,
    bgUrl = this.bgUrl,
    app = this.app.transform { it?.toDiscoverAppDetailEntity() }
)

fun RecommendCard.toRecommendCardEntity() = RecommendCardEntity(
    title = this.title,
    subTitle = this.subTitle,
    apps = this.apps.map { it.toDiscoverAppDetailEntity() }
)

fun DiscoverCardTable.toDiscoverCardDetail() = DiscoverCardDetail(
    cardType = this.cardType,
    card = this.detail.toDiscoverCard(cardType),
    id = this.id.toInt()
)

fun DiscoverCardDetailEntity.toDiscoverCard(cardType: Int): DiscoverCard {
    return when (cardType) {
        1 -> (this as BigDiscoverCardEntity).toBigDiscoverMultipleAppsCard()
        2 -> (this as SmallDiscoverCardEntity).toSmallDiscoverCard()
        3 -> (this as RecommendCardEntity).toRecommendCard()
        else -> throw RuntimeException("cardType不存在$cardType")
    }
}

fun BigDiscoverCardEntity.toBigDiscoverMultipleAppsCard() = BigDiscoverMultipleAppsCard(
    title = this.title,
    subTitle = this.subTitle,
    bgUrl = this.bgUrl,
    desc = this.desc,
    apps = this.apps.map { it.toDiscoverAppDetail() }.toMutableList()
)

fun DiscoverAppDetailEntity.toDiscoverAppDetail() = DiscoverAppDetail(
    name = this.name,
    desc = this.desc,
    iconUrl = this.iconUrl,
    deeplink = this.deeplink
)

fun SmallDiscoverCardEntity.toSmallDiscoverCard() = SmallDiscoverCard(
    title = this.title,
    subTitle = this.subTitle,
    desc = this.desc,
    bgUrl = this.bgUrl,
    app = this.app.transform { it?.toDiscoverAppDetail() }
)

fun RecommendCardEntity.toRecommendCard() = RecommendCard(
    title = this.title,
    subTitle = this.subTitle,
    apps = this.apps.map { it.toDiscoverAppDetail() }.toMutableList()
)