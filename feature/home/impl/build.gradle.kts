plugins {
    alias(libs.plugins.compose.demo.android.feature.impl)
}

android {
    namespace = "yz.l.compose.home.impl"
}

dependencies {
    implementation(projects.feature.home.api)
    implementation(projects.feature.lottery.api)
    implementation(projects.feature.discover.api)
}