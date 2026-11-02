plugins {
    alias(libs.plugins.compose.demo.android.feature.impl)
}

android {
    namespace = "yz.l.compose.home.impl"
}

dependencies {
    implementation(libs.androidx.media3.common.ktx)
    implementation(projects.feature.home.api)
    implementation(projects.feature.home.data)
    implementation(projects.feature.login.api)
    implementation(projects.core.media)
}