plugins {
    alias(libs.plugins.compose.demo.android.library)
    alias(libs.plugins.compose.demo.network)
}

android {
    namespace = "yz.l.network"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.com.google.code.gson.gson)
    implementation(projects.core.coreTool)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
}