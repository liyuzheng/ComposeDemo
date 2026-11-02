plugins {
    alias(libs.plugins.compose.demo.android.library.compose)
}

android {
    namespace = "yz.l.media"
}

dependencies {
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.ui.compose.material3)
    implementation(libs.androidx.media3.datasource.okhttp)
}