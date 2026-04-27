package yz.l.compose.halomodule.exts

import org.gradle.api.Project
import yz.l.compose.halomodule.constants.PluginConstant.MAVEN_CONFIG_FILE_NAME
import yz.l.compose.halomodule.constants.PluginConstant.PACKAGE_PATH
import yz.l.compose.halomodule.maven.PublishLocalMaven.MAVEN_PATH
import yz.l.compose.halomodule.utils.FileUtil
import java.io.File

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

fun Project.isJvmLib(): Boolean {
    return project.plugins.hasPlugin("org.jetbrains.kotlin.jvm")
}

fun Project.hasRepo(): Boolean {
    val outputDir =
        File(
            project.rootProject.rootDir.absolutePath
                    + "$MAVEN_PATH$PACKAGE_PATH/${
                FileUtil.getFlatAarName(project)
            }/$MAVEN_CONFIG_FILE_NAME"
        )
    val hasRepo = outputDir.exists()
    println("${project.name} hasrepo $hasRepo  ${outputDir.absolutePath}")
    return hasRepo
}