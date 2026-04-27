package yz.l.compose.feature.common.room.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * desc:
 * created by liyuzheng on 2026/4/27 17:12
 */
@Keep
@Entity(tableName = "track_table")
data class TrackEntity(
    val remoteName: String = "",
    @PrimaryKey
    val trackId: Int,
    val name: String,
    val duration: Int = 0,
    val artistId: Int = 0,
    val artistName: String = "",
    val albumName: String = "",
    val albumId: Int = 0,
    val position: Int = 0,
    val releasedData: String = "",
    val albumImage: String = "",
    val audio: String = "",
    val image: String = ""
)


//"id":"43886",
//"name":"Let Me Go",
//"album_id":"5283",
//"artist_id":"4777",
//"duration":"129",
//"artist_name":"STEEP",
//"playlistadddate":"2012-01-04 00:00:00",
//"position":"1",
//"license_ccurl":"http:\/\/creativecommons.org\/licenses\/by-nc-sa\/3.0\/",
//"album_image":"https:\/\/usercontent.jamendo.com?type=album&id=5283&width=300&trackid=43886",
//"image":"https:\/\/usercontent.jamendo.com?type=album&id=5283&width=300&trackid=43886",
//"audio":"https:\/\/prod-1.storage.jamendo.com\/?trackid=43886&format=mp31&from=0hm1uccNp1iJ0e66Z%2Fz9iA%3D%3D%7CrPOHEva1emnyu75anRKV6w%3D%3D",
//"audiodownload":"https:\/\/prod-1.storage.jamendo.com\/download\/track\/43886\/mp32\/",
//"audiodownload_allowed":true

//
//"id":"1848357",
//"name":"ma\u00f1ana ser\u00e1 tarde",
//"duration":272,
//"artist_id":"421168",
//"artist_name":"fankel",
//"artist_idstr":"fankel",
//"album_name":"ma\u00f1ana ser\u00e1 tarde",
//"album_id":"368084",
//"license_ccurl":"http:\/\/creativecommons.org\/licenses\/by-nc-nd\/3.0\/",
//"position":1,
//"releasedate":"2021-04-11",
//"album_image":"https:\/\/usercontent.jamendo.com?type=album&id=368084&width=300&trackid=1848357",
//"audio":"https:\/\/prod-1.storage.jamendo.com\/?trackid=1848357&format=mp31&from=app-devsite&u=9445777",
//"audiodownload":"https:\/\/prod-1.storage.jamendo.com\/download\/track\/1848357\/mp32\/?u=9445777",
//"prourl":"",
//"shorturl":"https:\/\/jamen.do\/t\/1848357",
//"shareurl":"https:\/\/www.jamendo.com\/track\/1848357",
//"waveform":"{
//"image":"https:\/\/usercontent.jamendo.com?type=album&id=368084&width=300&trackid=1848357",
//"musicinfo":{
//    "vocalinstrumental":"instrumental",
//    "lang":"",
//    "gender":"",
//    "acousticelectric":"electric",
//    "speed":"high",
//    "tags":{
//        "genres":[
//        "rock"
//        ],
//        "instruments":[
//        "bass",
//        "guitar"
//        ],
//        "vartags":[
//        "groovy",
//        "happy"
//        ]
//    }
//},
//"audiodownload_allowed":true,
//"content_id_free":false