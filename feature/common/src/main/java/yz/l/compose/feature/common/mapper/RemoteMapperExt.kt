package yz.l.compose.feature.common.mapper

import yz.l.compose.feature.common.paging.RemoteModel
import yz.l.compose.feature.common.room.entity.RemoteEntity

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:52
 */

fun RemoteEntity.toRemoteModel() = RemoteModel(name, next, prepend)
fun RemoteModel.toRemoteEntity() = RemoteEntity(name, next, prepend)