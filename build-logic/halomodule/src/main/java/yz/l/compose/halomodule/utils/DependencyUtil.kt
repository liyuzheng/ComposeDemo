package yz.l.compose.halomodule.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
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
object DependencyUtil {
    var i = 0
    var projectCount = 0

    fun start(project: Project) {
        val depsss = hashMapOf<String, DependencyNode1>()
        LogUtil.v("DependencyUtil :start")
        val startTime = System.currentTimeMillis()
        forEachDeps(depsss, project.rootProject)
        depsss.forEach { (string, node) ->
            LogUtil.v("DependencyUtil :forEachDeps -> $string $node")
        }

//        depsss.forEach { (name, node) ->
//            node.child.forEach {
//                depsss[it.dep.name]?.parents?.add(name)
//            }
//        }
//
//        depsss.forEach { (name, node) ->
//            if (name == "IMLib") return@forEach
//            node.child.forEach { dc ->
//                val dep = dc.dep
//                if (dep is ProjectDependency) {
//                    val aarName = FileUtil.getFlatAarName(dep.path)
//                    val aarFilePath =
//                        FileUtil.getLocalMavenCacheDir(project) + aarName + ".aar"
//                    if (File(aarFilePath).exists()) {
//                        val map = hashMapOf<String, String>()
//                        map["name"] = FileUtil.getFlatAarName(dep.path)
//                        map["ext"] = "aar"
//                        project.dependencies.add(dc.con.name, map)
//                        addDeps(project, depsss, map, node, dc.con.name)
//                        dc.con.dependencies.remove(dc.dep)
//                        LogUtil.v("deps: ${node.project.name} remove:${dc.dep.name}")
//                    } else {
//                        LogUtil.v("deps: $aarFilePath not Exists")
//                    }
//                } else {
//                    if (dc.dep.name == "implementation") {
//                        project.dependencies.add(dc.dep.name, dep)
//                    }
//                    addDeps(project, depsss, dep, node, dc.con.name)
//                }
//            }
//        }
//        LogUtil.v("DependencyUtil :end ->use:${System.currentTimeMillis() - startTime}ms")
    }

    private fun addDeps(
        app: Project,
        depsss: HashMap<String, DependencyNode1>,
        dep: Dependency,
        node: DependencyNode1?,
        dpName: String,
    ) {
        node ?: return
        node.project.dependencies.add(dpName, dep)
        if (dpName == "api" || dpName == "compile") {
            node.parents.forEach {
                val p = depsss[it] ?: return@forEach
                addDeps(app, depsss, dep, p, dpName)
            }
        }
    }

    private fun addDeps(
        app: Project,
        depsss: HashMap<String, DependencyNode1>,
        map: HashMap<String, String>,
        node: DependencyNode1?,
        dpName: String,
    ) {
        node ?: return
        node.project.dependencies.add(dpName, map)

        if (dpName == "api" || dpName == "compile") {
            node.parents.forEach {
                val p = depsss[it] ?: return@forEach
                addDeps(app, depsss, map, p, dpName)
            }
        }
    }

    private fun forEachDeps(
        depsss: HashMap<String, DependencyNode1>,
        project: Project
    ) {
        project.subprojects.forEach {
            projectCount += 1
            if (it.subprojects.isEmpty()) {
                forEachDeps1(depsss, it)
            }
        }
    }

    private fun forEachDeps1(depsss: HashMap<String, DependencyNode1>, project: Project) {
        val node = if (depsss[project.name] == null)
            DependencyNode1(project) else depsss[project.name]
        node ?: return
        depsss[project.name] = node
        project.configurations.filter { c ->
            c.name == "implementation"
                    || c.name == "api"
                    || c.name == "compile"
        }.forEach { c ->
            c.dependencies.filter { d ->
                d is ProjectDependency
                        || c.name == "api"
                        || c.name == "compile"
            }.forEach depForEach@{
                i += 1
                node.child.add(DependencyCons(c, it))
            }
        }
    }


    fun applyDependency(app: Project, modifyModule: MutableList<String>) {
        val startTime = System.currentTimeMillis()
        LogUtil.v("applyDependency -> start buildDependencyTree")
        val tree = buildDependencyTree(app, modifyModule)
        LogUtil.v("applyDependency -> end buildDependencyTree use:${System.currentTimeMillis() - startTime}ms")
        val startTime2 = System.currentTimeMillis()
        LogUtil.v("applyDependency -> start resolveDependencies")
        resolveDependencies(tree, modifyModule)
        LogUtil.v("end resolveDependencies use:${System.currentTimeMillis() - startTime2}ms")
//        printTree(tree)

    }

    private fun printTree(tree: DependencyNode) {
        if (tree.childProject.isNotEmpty())
            LogUtil.v("DependencyTree -> ${tree.projectName}")
        tree.childProject.forEach {
            LogUtil.v("DependencyTreeC-> ${it.dependencyName} ${it.projectName}")
        }
        tree.childProject.forEach {
            printTree(it)
        }
    }


    private fun buildDependencyTree(
        app: Project,
        modifyModule: MutableList<String>
    ): DependencyNode {
        val tree = DependencyNode(app.name, app, null, "")
        buildDependencyTree(tree, app, modifyModule)
        return tree
    }

    private fun buildDependencyTree(
        tree: DependencyNode,
        project: Project,
        modifyModule: MutableList<String>
    ) {
        project.configurations.forEach { c ->
            c.dependencies.filter { dependency ->
                dependency is DefaultProjectDependency && c.name in PluginConstant.DEPENDENCY_NAMES
            }.forEach childForEach@{
//                val child = (it as DefaultProjectDependency).dependencyProject
//                val existsNode = tree.childProject.find { n -> n.projectName == child.name }
//                if ((existsNode != null && existsNode.dependencyName == "api" && c.name == "api")
//                    || existsNode == null
//                    || (existsNode.dependencyName == "compile" && c.name == "compile")
//                ) {
//                    val childNode = DependencyNode(child.name, child, tree, c.name)
//                    tree.childProject.add(childNode)
//                    buildDependencyTree(childNode, child, modifyModule)
//                }
            }
        }
    }

    private fun resolveDependencies(tree: DependencyNode, modifyModule: MutableList<String>) {
        if (tree.childProject.isEmpty()) {
            doDependency(tree, modifyModule)
        } else {
            tree.childProject.forEach {
                resolveDependencies(it, modifyModule)
                doDependency(tree, modifyModule)
            }
        }
    }

    private fun doDependency(tree: DependencyNode, modifyModule: MutableList<String>) {
//        val parent = tree.parent?.project ?: return
//        parent.configurations.forEach { c ->
//            c.dependencies.filter { dependency ->
//                dependency is DefaultProjectDependency
//                        && c.name in PluginConstant.DEPENDENCY_NAMES
//            }.forEach dependency@{
//                val project = (it as DefaultProjectDependency).dependencyProject
//                val name = FileUtil.getFlatAarName(project)
//                val map = hashMapOf<String, String>()
//                map["name"] = name
//                map["ext"] = "aar"
//                if (project.name in modifyModule) return@dependency
//                val aarFilePath = FileUtil.getLocalMavenCacheDir(project) + name + ".aar"
//                if (File(aarFilePath).exists()) {
//                    c.dependencies.remove(it)
//                    addDependency(tree, c.name, map, modifyModule)
//                }
//            }
//        }
    }

    private fun addDependency(
        tree: DependencyNode,
        type: String,
        map: HashMap<String, String>,
        modifyModule: MutableList<String>
    ) {
        if (type == "api" || type == "compile") {
            val parent = tree.parent ?: return
            parent.project.dependencies.add("api", map)
            LogUtil.v("dependencies ->project:${parent.project.name} add:${map["name"]}.aar type:${type}")
            addDependency(parent, type, map, modifyModule)
        } else {
            val parent = tree.parent ?: return
            parent.project.dependencies.add("implementation", map)
            LogUtil.v("dependencies ->project:${parent.project.name} add:${map["name"]}.aar type:${type}")
        }
    }
}