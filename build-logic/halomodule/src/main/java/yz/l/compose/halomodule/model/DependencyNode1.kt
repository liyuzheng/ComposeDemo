package yz.l.compose.halomodule.model

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency

/**
 * desc: todo
 * createed by liyuzheng on 2022/7/29 16:08
 */
data class DependencyNode1(
    val project: Project,
    val parents: HashSet<String> = hashSetOf(),
    val child: MutableList<DependencyCons> = mutableListOf()
)

data class DependencyCons(
    val con: Configuration,
    val dep: Dependency
)