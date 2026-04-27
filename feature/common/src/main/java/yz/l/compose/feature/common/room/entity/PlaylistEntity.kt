package yz.l.compose.feature.common.room.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * desc:
 * created by liyuzheng on 2026/4/27 17:44
 */
@Keep
@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    val remoteName: String = "",
    @PrimaryKey
    val playlistId: Int = 0,
    val name: String = "",
    val creationDate: String = "",
    val userId: Int = 0,
    val userName: String = "",
    val zip: String = ""
)

//"id":"218277",
//"name":"some cool stuff",
//"creationdate":"2012-01-04",
//"user_id":"1293438",
//"user_name":"hywayace",
//"zip":"https:\/\/storage.jamendo.com\/download\/p218277\/mp32\/?u=9445777",
//"shorturl":"https:\/\/jamen.do\/l\/p218277",
//"shareurl":"https:\/\/www.jamendo.com\/list\/p218277"