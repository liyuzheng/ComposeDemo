package yz.l.compose.halomodule.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.work.Incremental
import org.gradle.work.InputChanges
import java.security.MessageDigest

/**
 * desc:
 * createed by liyuzheng on 2022/7/25 11:59
 */
abstract class PublishTask : DefaultTask() {

    @get:Incremental
    @get:InputFile
    abstract val inputDir: RegularFileProperty

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @Internal
    override fun getGroup(): String? {
        super.getGroup()
        return "halo module"
    }

    @TaskAction
    fun action(inputChanges: InputChanges) {
        println("start PublishTask ${project.name}")
        if (inputChanges.isIncremental) return

        val aarFile = inputDir.get().asFile
        val md5Hex = calculateMD5(aarFile)

        println("正在处理文件 aarMd5: $md5Hex")
        outputFile.get().asFile.writeText("Last processed: $md5Hex")
    }

    private fun calculateMD5(file: java.io.File): String {
        val md = MessageDigest.getInstance("MD5")
        file.inputStream().use { isStream ->
            val buffer = ByteArray(8192)
            var read: Int
            while (isStream.read(buffer).also { read = it } > 0) {
                md.update(buffer, 0, read)
            }
        }
        return md.digest().joinToString("") { "%02x".format(it) }
    }
}