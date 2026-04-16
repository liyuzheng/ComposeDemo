package yz.l.compose.halomodule.maven

import groovy.util.Node
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.maven
import yz.l.compose.halomodule.constants.MAVEN_LOCAL_DIR
import yz.l.compose.halomodule.constants.PluginConstant
import yz.l.compose.halomodule.utils.FileUtil
import java.io.File

/**
 * desc: todo
 * createed by liyuzheng on 2022/8/15 12:05
 */
object PublishLocalMaven {
    const val MAVEN_PATH = "/.gradle/output/.haloModule/.repo"
    const val MAVEN_GROUP = "com.starmaker.halomodule"
    const val MAVEN_VERSION = "1.0.0-SNAPSHOT"
    fun configLocalAarMavenPublication(
        project: Project
    ) {
        val publishingExt = project.extensions.getByType(
            PublishingExtension::class.java
        )
        val localAarMavenRepository = project.rootProject.layout.projectDirectory.dir("local-repo")
        println("PublishLocalMaven uri is $localAarMavenRepository")
        publishingExt.repositories {
            maven {
                url = localAarMavenRepository.asFile.toURI()
            }
        }
        val publications = publishingExt.publications

        val folder = File(project.buildDir.absolutePath + "/outputs/aar")
        if (!folder.exists()) return
        folder.listFiles()?.forEach { file ->
            if (!file.name.endsWith(".aar")) {
                return@forEach
            }
            val publishName: String = FileUtil.getFlatAarName(project)
            if (publications.find { it.name == publishName } != null) return
            publications.create(
                publishName,
                MavenPublication::class.java
            ) {
                this.artifact(file.absolutePath)
                this.groupId = MAVEN_GROUP
                this.artifactId = publishName
                this.version = MAVEN_VERSION
                this.pom.withXml {
                    val dependenciesNode = this.asNode().appendNode("dependencies")
                    project.configurations.filter { c ->
                        c.name in PluginConstant.DEPENDENCY_NAMES
                    }.forEach { c ->
                        c.dependencies
                            .forEach dp@{ d ->
                                if (d.name == "unspecified" || d.version == null || d.group == null)
                                    return@dp
                                if (d.name == "firebase-bom" && d.group == "com.google.firebase") {
                                    buildFireBaseBomDeps(dependenciesNode, d)
                                } else if (d is DefaultProjectDependency) {
                                    buildProjectDeps(dependenciesNode, d)
                                } else {
                                    buildDefaultDeps(dependenciesNode, d)
                                }
                            }
                    }
                    println("pom -----> $this")
                }
            }
        }
    }

    private fun buildFireBaseBomDeps(dependenciesNode: Node, d: Dependency) {
        val dependencyNode = dependenciesNode.appendNode("dependency")
        dependencyNode.appendNode("groupId", "com.google.firebase")
        dependencyNode.appendNode("artifactId", "firebase-perf")
        dependencyNode.appendNode("version", "20.1.0")

        val dependencyNode1 = dependenciesNode.appendNode("dependency")
        dependencyNode1.appendNode("groupId", "com.google.android.gms")
        dependencyNode1.appendNode(
            "artifactId",
            "play-services-measurement-api"
        )
        dependencyNode1.appendNode("version", "21.1.0")

        val dependencyNode2 = dependenciesNode.appendNode("dependency")
        dependencyNode2.appendNode("groupId", d.group)
        dependencyNode2.appendNode(
            "artifactId",
            d.name
        )
        dependencyNode2.appendNode("version", d.version)
    }

    private fun buildProjectDeps(dependenciesNode: Node, d: DefaultProjectDependency) {
        println("pom---- buildProjectDeps")
        val dependencyNode = dependenciesNode.appendNode("dependency")
        dependencyNode.appendNode("groupId", MAVEN_GROUP)
        dependencyNode.appendNode(
            "artifactId",
            d.path.substring(1).replace(":", "-")
        )
        dependencyNode.appendNode("version", MAVEN_VERSION)
    }

    private fun buildDefaultDeps(dependenciesNode: Node, d: Dependency) {
        val dependencyNode = dependenciesNode.appendNode("dependency")
        if (d is DefaultExternalModuleDependency) {
            if (d.artifacts.find { da ->
                    da.type == "aar"
                } != null) {
                dependencyNode.appendNode("type", "aar")
            }
        }
        println("${d.group}:${d.name}:${d.version} $d")
        dependencyNode.appendNode("groupId", d.group)
        dependencyNode.appendNode(
            "artifactId",
            d.name
        )
        dependencyNode.appendNode("version", d.version)
    }
}