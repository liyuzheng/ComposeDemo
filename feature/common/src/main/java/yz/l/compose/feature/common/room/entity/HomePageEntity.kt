package yz.l.compose.feature.common.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * desc:
 * created by liyuzheng on 2026/4/27 15:36
 */
@Entity(tableName = "home_page_index_table")
data class HomePageIndexEntity(
    @PrimaryKey val feedId: Long,
    val type: String,
    val remoteName: String = "",
    val position: Int = 0
)

// itemType 0radio 1track 2playlist
@Entity(tableName = "feed_item_ref", primaryKeys = ["parentFeedId", "itemId", "remoteName"])
data class FeedItemRef(
    val remoteName: String = "",
    val parentFeedId: Long,
    val itemId: Int,
    val itemType: Int // 区分是 Song 还是 Radio
)

data class HomeItem(
    @Embedded val feed: HomePageIndexEntity,

    @Relation(
        parentColumn = "feedId",
        entityColumn = "trackId",
        associateBy = Junction(
            value = FeedItemRef::class,
            parentColumn = "parentFeedId",
            entityColumn = "itemId"
        )
    )
    val tracks: List<TrackEntity> = emptyList(),

    @Relation(
        parentColumn = "feedId",
        entityColumn = "radioId",
        associateBy = Junction(
            value = FeedItemRef::class,
            parentColumn = "parentFeedId",
            entityColumn = "itemId"
        )
    )
    val radios: List<RadioEntity> = emptyList(),

    @Relation(parentColumn = "feedId", entityColumn = "playlistId") // 假设歌单是一对一或简单的
    val playlist: PlaylistEntity? = null
)