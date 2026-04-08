plugins {
    alias(libs.plugins.compose.demo.android.library.compose)
}

android {
    namespace = "yz.l.compose.theme"
}

dependencies {
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.compose.material3)
}