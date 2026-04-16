package yz.l.compose.halomodule.utils

import org.gradle.api.Project
import java.io.File

/**
 * description:
 * author chaojiong.zhang
 * data: 2021/11/1
 * copyright TCL+
 */
object FileUtil {
    var outputDir = ""

    fun init(project: Project) {
        outputDir = project.projectDir.absolutePath + "/.gradle/output/.haloModule/"
    }

    fun getFlatAarName(project: Project): String {
        return project.path.substring(1).replace(":", "-")
    }

    fun deleteFileOrFolder(file: File) {
        if (file.isFile) file.delete()
        else if (file.isDirectory) {
            file.listFiles()?.forEach {
                deleteFileOrFolder(it)
            }
        }
    }
}






