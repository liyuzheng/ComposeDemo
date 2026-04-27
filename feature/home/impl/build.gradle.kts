plugins {
    alias(libs.plugins.compose.demo.android.feature.impl)
}

android {
    namespace = "yz.l.compose.home.impl"
}

dependencies {
    implementation(projects.feature.home.api)
    implementation(projects.feature.home.data)
    implementation(projects.feature.login.api)
}