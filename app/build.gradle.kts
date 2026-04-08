plugins {
    alias(libs.plugins.compose.demo.android.application.compose)
    alias(libs.plugins.compose.demo.network)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "yz.l.compose.demo"

    defaultConfig {
        applicationId = "yz.l.compose.demo"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.dynamicanimation)
    implementation(libs.androidx.dynamicanimation.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    implementation(libs.androidx.compose.material3.adaptive.navigation3)
    implementation(libs.androidx.startup.runtime)
    implementation(libs.com.tencent.mmkv.static)
    implementation(libs.kotlinx.serialization.json)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(projects.core.network)
    implementation(projects.core.coreNavigation)
    implementation(projects.core.theme)
    implementation(projects.core.coreTool)

    implementation(projects.feature.lottery.api)
    implementation(projects.feature.lottery.impl)
    implementation(projects.feature.login.api)
    implementation(projects.feature.login.impl)
    implementation(projects.feature.login.data)
    implementation(projects.feature.home.impl)
    implementation(projects.feature.home.api)
    implementation(projects.feature.common)
    implementation(projects.feature.discover.api)
    implementation(projects.feature.discover.impl)
    implementation(projects.feature.discover.data)

}