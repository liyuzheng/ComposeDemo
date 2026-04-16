package yz.l.compose.halomodule.exts

import org.gradle.api.Project

/**
 * desc:
 * created by liyuzheng on 2026/4/16 20:54
 */

fun Project.isLibrary(): Boolean {
    return project.plugins.hasPlugin("com.android.library")
}

fun Project.isApplication(): Boolean {
    return project.plugins.hasPlugin("com.android.application")
}