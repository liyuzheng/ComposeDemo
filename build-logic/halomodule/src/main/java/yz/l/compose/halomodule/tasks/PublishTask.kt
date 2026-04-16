package yz.l.compose.halomodule.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.work.Incremental
import org.gradle.work.InputChanges

/**
 * desc:
 * createed by liyuzheng on 2022/7/25 11:59
 */
abstract class PublishTask : DefaultTask() {

    @get:Incremental
    @get:InputDirectory
    abstract val inputDir: DirectoryProperty


    @Internal
    override fun getGroup(): String? {
        super.getGroup()
        return "halo module"
    }

    @TaskAction
    fun action(inputChanges: InputChanges) {
        println("start PublishTask ${project.name}")
        if (inputChanges.isIncremental) return

    }
}