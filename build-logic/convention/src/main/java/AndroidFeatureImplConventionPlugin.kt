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
 * created by liyuzheng on 2026/3/16 18:47
 */
class AndroidFeatureImplConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "compose.demo.android.library.compose")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
            dependencies {
                "implementation"(libs.findLibrary("androidx.navigation3.runtime").get())
                "implementation"(libs.findLibrary("androidx.navigation3.ui").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewModel.navigation3").get())
                "implementation"(
                    libs.findLibrary("androidx.compose.material3.adaptive.navigation3").get()
                )
                "debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling").get())
                "implementation"(libs.findLibrary("androidx.compose.material3").get())
                "implementation"(libs.findLibrary("coil.kt").get())
                "implementation"(libs.findLibrary("coil.kt.compose").get())
                "implementation"(libs.findLibrary("coil.kt.svg").get())
                "implementation"(project(":core:core_navigation"))

            }
        }
    }
}
