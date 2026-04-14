plugins {
    alias(libs.plugins.compose.demo.android.feature.impl)
}

android {
    namespace = "yz.l.compose.game.impl"
}

dependencies {
    implementation(projects.feature.game.api)
    implementation(projects.feature.game.data)
}