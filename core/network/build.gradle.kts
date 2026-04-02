plugins {
    alias(libs.plugins.compose.demo.android.library)
    alias(libs.plugins.compose.demo.network)
}

android {
    namespace = "yz.l.network"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.com.google.code.gson.gson)
    implementation(projects.core.coreTool)
}