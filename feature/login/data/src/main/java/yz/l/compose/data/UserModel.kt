package yz.l.compose.data

import kotlinx.serialization.Serializable

/**
 * desc:
 * createed by liyuzheng on 2023/8/26 14:19
 */
@Serializable
data class UserModel(
    var uid: String = "",
    var name: String = "",
    var gender: Int = -1
)
