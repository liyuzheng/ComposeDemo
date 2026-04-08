import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import yz.l.compose.convention.exts.configureAndroidCompose
import yz.l.compose.convention.exts.libs

/**
 * desc:
 * created by liyuzheng on 2026/3/24 21:04
 */
class AndroidFeatureDataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "compose.demo.android.library.compose")
            apply(plugin = "kotlin-parcelize")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
            dependencies {
                "implementation"(libs.findLibrary("com.tencent.mmkv.static").get())
                "implementation"(libs.findLibrary("com.google.code.gson.gson").get())
                "implementation"(libs.findLibrary("kotlinx.serialization.json").get())
                "implementation"(project(":core:core_tool"))
                "implementation"(project(":core:network"))
                "implementation"(project(":feature:common"))
            }
        }
    }
}