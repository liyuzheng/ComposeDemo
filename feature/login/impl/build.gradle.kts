plugins {
    alias(libs.plugins.compose.demo.android.feature.impl)
}

android {
    namespace = "yz.l.compose.login.impl"
}

dependencies {
    implementation(projects.feature.login.api)
    implementation(projects.feature.login.data)
    implementation(projects.feature.lottery.api)
}