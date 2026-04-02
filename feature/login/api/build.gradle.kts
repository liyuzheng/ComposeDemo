plugins {
    alias(libs.plugins.compose.demo.android.feature.api)
}

android {
    namespace = "yz.l.compose.login.api"
}

dependencies {
    implementation(projects.feature.login.data)
}