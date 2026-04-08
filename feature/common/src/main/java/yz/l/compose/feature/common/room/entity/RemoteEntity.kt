package yz.l.compose.feature.common.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteEntity(
    @PrimaryKey
    var name: String = "",
    var next: String = "",
    var prepend: String = ""
)