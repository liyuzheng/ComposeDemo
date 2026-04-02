plugins {
    alias(libs.plugins.compose.demo.android.library.compose)
}

android {
    namespace = "yz.l.core_router"
}

dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    implementation(libs.androidx.compose.material3.adaptive.navigation3)
}