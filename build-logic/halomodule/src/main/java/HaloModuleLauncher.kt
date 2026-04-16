import org.gradle.api.Plugin
import org.gradle.api.Project

class HaloModuleLauncher : Plugin<Project> {
    override fun apply(appProject: Project) {
        if (!appProject.plugins.hasPlugin("com.android.application")) {
            appProject.logger.warn("插件 A 应该被应用在 App 模块中")
        }

        val rootProject = appProject.rootProject
        rootProject.subprojects {
            val sub = this
            sub.pluginManager.apply("compose.demo.halo.module")
//            try {
//
//            } catch (e: Exception) {
//                sub.logger.error("模块 ${sub.name} 应用插件 HaloModule 失败: ${e.message}")
//            }
        }
    }
}
