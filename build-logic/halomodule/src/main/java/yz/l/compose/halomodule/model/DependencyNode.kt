package yz.l.compose.halomodule.model

import org.gradle.api.Project

/**
 * desc: todo
 * createed by liyuzheng on 2022/7/28 16:30
 */
data class DependencyNode(
    val projectName: String, //当前项目名称
    val project: Project, //当前项目对象
    val parent: DependencyNode?, //依赖当前项目的对象
    val dependencyName: String, //依赖方式  api/implementation
    val childProject: MutableList<DependencyNode> = mutableListOf() //当前项目依赖的项目
)