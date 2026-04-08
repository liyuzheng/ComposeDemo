plugins {
    alias(libs.plugins.compose.demo.android.library.compose)
}

android {
    namespace = "yz.l.core_tool"
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.com.google.code.gson.gson)
    implementation(libs.com.tencent.mmkv.static)
    implementation(libs.accompanist.permissions)
    implementation(libs.kotlinx.serialization.json)
    api(libs.timber)
}