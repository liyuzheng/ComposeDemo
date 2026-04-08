plugins {
    alias(libs.plugins.compose.demo.android.feature.impl)
}

android {
    namespace = "yz.l.compose.discover.impl"
}

dependencies {
    implementation(projects.feature.discover.api)
    implementation(projects.feature.discover.data)
}