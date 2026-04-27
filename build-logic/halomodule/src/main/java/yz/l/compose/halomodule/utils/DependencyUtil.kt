package yz.l.compose.halomodule.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.attributes.Category
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency
import yz.l.compose.halomodule.constants.PluginConstant
import yz.l.compose.halomodule.model.DependencyCons
import yz.l.compose.halomodule.model.DependencyNode
import yz.l.compose.halomodule.model.DependencyNode1
import java.io.File

/**
 * desc:
 * createed by liyuzheng on 2022/7/28 16:26
 */
fun isBom(dep: Dependency?): Boolean {
    if (dep !is ModuleDependency) return false
    val category = dep.attributes.getAttribute(Category.CATEGORY_ATTRIBUTE)?.name
    return category == Category.REGULAR_PLATFORM || category == Category.ENFORCED_PLATFORM
}

fun ProjectDependency.getProject(project: Project): Project {
    return project.rootProject.project(this.path)
}