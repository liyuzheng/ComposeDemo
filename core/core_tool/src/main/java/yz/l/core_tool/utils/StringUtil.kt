package yz.l.core_tool.utils

import kotlin.random.Random

/**
 * desc:
 * created by liyuzheng on 2026/4/24 19:04
 */
fun randomString(length: Int, includeDigits: Boolean = true): String {
    val baseChars = ('A'..'Z') + ('a'..'z')
    val digits = ('0'..'9')
    val pool = if (includeDigits) baseChars + digits else baseChars
    return (1..length)
        .map { pool[Random.nextInt(pool.size)] }
        .joinToString("")
}