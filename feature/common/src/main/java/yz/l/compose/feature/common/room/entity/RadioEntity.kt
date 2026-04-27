package yz.l.compose.feature.common.room.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * desc:
 * created by liyuzheng on 2026/4/27 16:40
 */
@Keep
@Entity(tableName = "radio_table")
data class RadioEntity(
    val remoteName: String = "",
    @PrimaryKey
    val radioId: Int = 0,
    val name: String = "",
    val displayName: String = "",
    val type: String = "",
    val image: String = "",
    val stream: String = ""
)

//{
//    "id":1,
//    "name":"bestof",
//    "dispname":"Best Of Jamendo Radio",
//    "type":"www",
//    "image":"https:\/\/images.jamendo.com\/new_jamendo_radios\/bestof150.jpg"
//}