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

    fun isModify(targetProject: Project): Boolean {
        return isModuleNewerThanAar(targetProject)
    }

    private fun isModuleNewerThanJar(targetProject: Project): Boolean {
        // 1. 定义 AAR 的位置 (根据你的 bundleDebugAar 产出路径)
        val jarFile = targetProject.layout.buildDirectory
            .file("outputs/libs/${targetProject.name}.jar")
            .get().asFile

        if (!jarFile.exists()) {
            return true // AAR 不存在，显然需要运行
        }

        val jarLastModified = jarFile.lastModified()

        // 2. 递归获取源码文件夹（src）和构建脚本的最大修改时间
        // 我们排除 build 目录，只看 src 和 build.gradle.kts
        val srcDir = targetProject.file("src")
        val buildScript = targetProject.file("build.gradle.kts")

        val filesToCheck = mutableListOf(srcDir, buildScript)

        var lastModifiedSource = 0L

        filesToCheck.forEach { file ->
            if (file.exists()) {
                val maxTimestamp = file.walkTopDown()
                    .filter { it.isFile && !it.path.contains("/build/") } // 过滤掉构建目录
                    .map { it.lastModified() }
                    .maxOrNull() ?: 0L

                if (maxTimestamp > lastModifiedSource) {
                    lastModifiedSource = maxTimestamp
                }
            }
        }

        // 3. 打印调试信息（可选）
        // println(">>> [${targetProject.name}] AAR Time: $aarLastModified, Src Time: $lastModifiedSource")

        // 如果源码的最后修改时间大于 AAR 的时间，说明代码变了
        val isModify = lastModifiedSource > jarLastModified
        println("${targetProject.path} isModify $isModify")
        return isModify
    }

    private fun isModuleNewerThanAar(targetProject: Project): Boolean {
        // 1. 定义 AAR 的位置 (根据你的 bundleDebugAar 产出路径)
        val aarFile = targetProject.layout.buildDirectory
            .file("outputs/aar/${targetProject.name}-debug.aar")
            .get().asFile

        if (!aarFile.exists()) {
            return true // AAR 不存在，显然需要运行
        }

        val aarLastModified = aarFile.lastModified()

        // 2. 递归获取源码文件夹（src）和构建脚本的最大修改时间
        // 我们排除 build 目录，只看 src 和 build.gradle.kts
        val srcDir = targetProject.file("src")
        val buildScript = targetProject.file("build.gradle.kts")

        val filesToCheck = mutableListOf(srcDir, buildScript)

        var lastModifiedSource = 0L

        filesToCheck.forEach { file ->
            if (file.exists()) {
                val maxTimestamp = file.walkTopDown()
                    .filter { it.isFile && !it.path.contains("/build/") } // 过滤掉构建目录
                    .map { it.lastModified() }
                    .maxOrNull() ?: 0L

                if (maxTimestamp > lastModifiedSource) {
                    lastModifiedSource = maxTimestamp
                }
            }
        }

        // 3. 打印调试信息（可选）
        // println(">>> [${targetProject.name}] AAR Time: $aarLastModified, Src Time: $lastModifiedSource")

        // 如果源码的最后修改时间大于 AAR 的时间，说明代码变了
        val isModify = lastModifiedSource > aarLastModified
        println("${targetProject.path} isModify $isModify")
        return isModify
    }
}






