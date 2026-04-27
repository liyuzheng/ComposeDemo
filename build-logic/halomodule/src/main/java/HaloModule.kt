import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.withType
import yz.l.compose.halomodule.exts.hasRepo
import yz.l.compose.halomodule.exts.isApplication
import yz.l.compose.halomodule.exts.isLibrary
import yz.l.compose.halomodule.maven.PublishLocalMaven
import yz.l.compose.halomodule.maven.PublishLocalMaven.MAVEN_GROUP
import yz.l.compose.halomodule.maven.PublishLocalMaven.MAVEN_VERSION
import yz.l.compose.halomodule.tasks.PublishTask
import yz.l.compose.halomodule.utils.FileUtil
import yz.l.compose.halomodule.utils.getProject

/**
 * desc:
 * created by liyuzheng on 2026/4/15 20:46
 */
class HaloModule : Plugin<Project> {
    override fun apply(project: Project) {
        setProjectDeps(project)
        val deps = mutableListOf<Dependency>()
        prepareTask(project)
        registerPublish(project)
        project.afterEvaluate {
            println("HaloModule apply ${project.path}")
            if (project.isApplication()) return@afterEvaluate

            val implementation = project.configurations.findByName("implementation")
            val api = project.configurations.findByName("api")
            val compileOnly = project.configurations.findByName("compileOnly")
            if (implementation != null && compileOnly != null && api != null) {
                val dependencies = implementation.dependencies.toList() + api.dependencies.toList()
                dependencies.forEach { dep ->
                    if (dep is ProjectDependency && dep.getProject(project)
                            .hasRepo() && !FileUtil.isModify(dep.getProject(project))
                    ) {
                        project.dependencies.add(
                            compileOnly.name, mapOf(
                                "group" to MAVEN_GROUP,
                                "name" to dep.path.replace(":", "-").removeRange(0, 1),
                                "version" to MAVEN_VERSION
                            )
                        )
                        println("add ProjectDependency ${dep.path.replace(":", "-")}")
                    } else {
                        project.dependencies.add(compileOnly.name, dep)
                    }
                    implementation.dependencies.remove(dep)
                    deps.add(dep)
                    val app = project.rootProject.project(":app")
                    val appImpl =
                        app.configurations.findByName("implementation") ?: return@afterEvaluate
                    val implConfig =
                        app.configurations.findByName("implementation") ?: return@afterEvaluate
                    val dependencies = appImpl.dependencies.toList()
                    if (dep !in dependencies && dep !is ProjectDependency) project.dependencies.add(
                        implConfig.name,
                        dep
                    )
                    println("app 添加 ${implConfig.name} ${dep.group}:${dep.name}:${dep.version} ${dep is ProjectDependency}")
                }
            }
        }
    }

    private fun setProjectDeps(project: Project) {
        println("setProjectDeps1 ")
        if (!project.isApplication()) return
        println("setProjectDeps2 ")
        project.afterEvaluate {
            println("setProjectDeps3 ")
            val configurations =
                project.configurations.findByName("implementation") ?: return@afterEvaluate
            val dependencies = configurations.dependencies.toList()
            dependencies.forEach { dep ->
                if (dep is ProjectDependency
                    && !FileUtil.isModify(dep.getProject(project))
                    && dep.getProject(project).hasRepo()
                ) {
                    configurations.dependencies.remove(dep)
                    println(
                        "setProjectDeps6  remove ${dep.group} ${dep.name} ${dep is ProjectDependency} ${
                            FileUtil.isModify(
                                project.project(dep.path)
                            )
                        }"
                    )
                }
            }
            println("setProjectDeps4 ")
            project.rootProject.allprojects.forEach { p ->
                p.plugins.forEach {
                    println("setProjectDeps5 ${p.displayName} $it")
                }
                if (p.hasRepo() && !FileUtil.isModify(p)) {
                    project.dependencies.add(
                        configurations.name, mapOf(
                            "group" to MAVEN_GROUP,
                            "name" to FileUtil.getFlatAarName(p),
                            "version" to MAVEN_VERSION
                        )
                    )
                    println(
                        "app 添加 ${configurations.name} ${MAVEN_GROUP}:${
                            FileUtil.getFlatAarName(
                                p
                            )
                        }:${MAVEN_VERSION}"
                    )
                }
            }
        }
    }

    private fun prepareTask(project: Project) {
        project.tasks.whenTaskAdded {
            if (!project.isLibrary()) return@whenTaskAdded
            when (name) {
                "preBuild" -> {

                }

                "bundleDebugAar" -> {
//                    val preBuildTask = project.tasks.findByName("preBuild")
//                    val publishToHaloModule = project.tasks.findByName("publishToHaloModule")
//                    preBuildTask?.finalizedBy(this)
//                    println("${project.name}1111111111 bundleDebugAar $publishToHaloModule")
//                    publishToHaloModule?.dependsOn(this)
//                    this.finalizedBy(publishToHaloModule ?: return@whenTaskAdded)

                }

                "publishToHaloModule" -> {
//                    val publishAllPublicationsToMavenRepository =
//                        project.tasks.named("publishAllPublicationsToMavenRepository")
//                    println("prepareTask  publishToHaloModule   publishAllPublicationsToMavenRepository is ${publishAllPublicationsToMavenRepository} ")
//                    this.finalizedBy(publishAllPublicationsToMavenRepository)
                }
            }
        }
    }

    private fun registerPublish(target: Project) {
        target.plugins.apply("maven-publish")
        PublishLocalMaven.configLocalAarMavenPublication(target)
        target.afterEvaluate {
            target.tasks.register("publishToHaloModule", PublishTask::class.java) {
                inputDir.set(project.file("./build/outputs/aar/${project.name}-debug.aar"))
                outputFile.set(project.file("./build/outputs/maker.txt"))
                doLast {
                    // 如果此代码运行了，说明文件夹内容一定变了
                    // 如果没变，Gradle 会显示 "UP-TO-DATE" 并跳过此 doLast
                    println("${target.path} 检测到文件夹内容已更新！")
                }
            }
            project.tasks.forEach { it ->
                if (it.name.contains("publish") || it.name.contains("aar")) println("${target.name}1111111111 ${it.name}")
            }

            project.tasks.matching { it.name == "preBuild" }.configureEach {
                finalizedBy(project.tasks.matching { it.name == "bundleDebugAar" })
            }

            project.tasks.matching { it.name == "bundleDebugAar" }.configureEach {
                finalizedBy(project.tasks.matching { it.name == "publishToHaloModule" })
            }

            project.tasks.matching { it.name == "publishToHaloModule" }.configureEach {
                finalizedBy(project.tasks.matching { it.name == "publishAllPublicationsToMavenRepository" })
            }

            project.tasks.withType<org.gradle.api.publish.maven.tasks.PublishToMavenRepository>()
                .configureEach {
                    val bundleTask =
                        tasks.matching { it.name.contains("bundleDebugAar", ignoreCase = true) }
                    dependsOn(bundleTask)
                    onlyIf {
                        println("2222 ${project.displayName} ${tasks.getByName("publishToHaloModule").didWork}}")
                        tasks.getByName("publishToHaloModule").didWork
                    }
                }
        }
    }
}