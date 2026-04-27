plugins {
    alias(libs.plugins.compose.demo.android.feature.impl)
}

android {
    namespace = "yz.l.compose.main.impl"
}

dependencies {
    implementation(projects.feature.main.api)
    implementation(projects.feature.lottery.api)
    implementation(projects.feature.discover.api)
    implementation(projects.feature.game.api)
    implementation(projects.feature.home.api)
    implementation(libs.airbnb.lottie.compose)
}