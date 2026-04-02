import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import yz.l.compose.convention.exts.libs

/**
 * desc:
 * created by liyuzheng on 2026/3/14 19:52
 */
abstract class AndroidNetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "implementation"(libs.findLibrary("com.squareup.okhttp3.okhttp").get())
                "implementation"(libs.findLibrary("com.squareup.okhttp3.logging.interceptor").get())
                "implementation"(libs.findLibrary("com.squareup.retrofit2.retrofit").get())
                "implementation"(libs.findLibrary("com.squareup.retrofit2.converter.gson").get())
            }
        }
    }
}