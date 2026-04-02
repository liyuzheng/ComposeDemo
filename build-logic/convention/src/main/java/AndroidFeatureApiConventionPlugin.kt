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
 * created by liyuzheng on 2026/3/16 18:00
 */
class AndroidFeatureApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "compose.demo.android.library.compose")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
            dependencies {
                "implementation"(libs.findLibrary("androidx.navigation3.runtime").get())
                "implementation"(libs.findLibrary("androidx.navigation3.ui").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewModel.navigation3").get())
                "implementation"(
                    libs.findLibrary("androidx.compose.material3.adaptive.navigation3").get()
                )
                "implementation"(project(":core:core_navigation"))
            }
        }
    }
}
