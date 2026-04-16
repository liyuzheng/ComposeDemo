import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.withType
import yz.l.compose.halomodule.exts.isApplication
import yz.l.compose.halomodule.exts.isLibrary
import yz.l.compose.halomodule.maven.PublishLocalMaven
import yz.l.compose.halomodule.tasks.PublishTask

/**
 * desc:
 * created by liyuzheng on 2026/4/15 20:46
 */
class HaloModule : Plugin<Project> {
    override fun apply(project: Project) {
        val deps = mutableListOf<Dependency>()
        registerPublish(project)
        prepareTask(project)
        project.afterEvaluate {
            println("HaloModule apply ${project.path}")
            if (project.isApplication()) return@afterEvaluate

            val implementation = project.configurations.findByName("implementation")
            val compileOnly = project.configurations.findByName("compileOnly")
            if (implementation != null && compileOnly != null) {
                val dependencies = implementation.dependencies.toList()
                dependencies.forEach { dep ->
                    project.dependencies.add(compileOnly.name, dep)
                    implementation.dependencies.remove(dep)
                    deps.add(dep)
                    val app = project.rootProject.project(":app")
                    val appImpl =
                        app.configurations.findByName("implementation") ?: return@afterEvaluate
                    val implConfig =
                        app.configurations.findByName("implementation") ?: return@afterEvaluate
                    val dependencies = appImpl.dependencies.toList()
                    if (dep !in dependencies)
                        project.dependencies.add(implConfig.name, dep)
                }
            }
        }
    }

    private fun prepareTask(project: Project) {
        project.tasks.whenTaskAdded {
            if (!project.isLibrary()) return@whenTaskAdded
            when (name) {
                "bundleDebugAar" -> {
                    val preBuildTask = project.tasks.findByName("preBuild")
                    val publishToHaloModule = project.tasks.findByName("publishToHaloModule")
                    preBuildTask?.finalizedBy(this)
                    println("prepareTask bundleDebugAar $publishToHaloModule")
                    publishToHaloModule?.dependsOn(this)
                    this.finalizedBy(publishToHaloModule ?: return@whenTaskAdded)

                }

                "publishToHaloModule" -> {
                    val publishAllPublicationsToMavenRepository =
                        project.tasks.named("publishAllPublicationsToMavenRepository")
                    println("prepareTask  publishToHaloModule   publishAllPublicationsToMavenRepository is ${publishAllPublicationsToMavenRepository} ")
                    this.finalizedBy(publishAllPublicationsToMavenRepository)
                }

                "publishAllPublicationsToMavenRepository" -> {
                    println("prepareTask publishAllPublicationsToMavenRepository")
                }
            }
        }
    }

    private fun registerPublish(target: Project) {
        target.plugins.apply("maven-publish")
        PublishLocalMaven.configLocalAarMavenPublication(target)
        target.afterEvaluate {
            target.tasks.register("publishToHaloModule", PublishTask::class.java) {
                inputDir.set(project.file("./build/outputs/aar"))
                outputs.file(project.file("./build/outputs/aar/${project.name}-debug.aar"))
                doLast {
                    // 如果此代码运行了，说明文件夹内容一定变了
                    // 如果没变，Gradle 会显示 "UP-TO-DATE" 并跳过此 doLast
                    println("检测到文件夹内容已更新！")
                }
            }


            project.tasks.withType<org.gradle.api.publish.maven.tasks.PublishToMavenRepository>()
                .configureEach {
                    // 显式依赖 bundle...Aar 任务
                    // 这里的名字要根据你的变体调整，通常是 bundleDebugAar 或 bundleReleaseAar
                    println("registerPublish  publishToHaloModule is ${project.tasks.findByName("publishToHaloModule")}")
                    dependsOn(
                        project.tasks.named("publishToHaloModule")
                    )
//                    onlyIf {
//                        val taskAInstance = tasks.getByName("publishToHaloModule")
//                        taskAInstance.didWork
//                    }
                }
        }
    }
}