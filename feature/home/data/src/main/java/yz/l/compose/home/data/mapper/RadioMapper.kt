package yz.l.compose.home.data.mapper

import yz.l.compose.feature.common.room.entity.RadioEntity
import yz.l.compose.home.data.RadioModel

/**
 * desc:
 * created by liyuzheng on 2026/4/27 18:55
 */
fun RadioModel.toRadioEntity(remoteName: String) = RadioEntity(
    remoteName = remoteName,
    radioId = radioId,
    name = name,
    displayName = displayName,
    type = type,
    image = image,
    stream = stream
)

fun RadioEntity.toRadioModel() = RadioModel(
    radioId = radioId,
    name = name,
    displayName = displayName,
    type = type,
    image = image,
    stream = stream
)