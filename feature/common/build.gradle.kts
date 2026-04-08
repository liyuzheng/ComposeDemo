plugins {
    alias(libs.plugins.compose.demo.android.feature.api)
    alias(libs.plugins.compose.demo.room)
}

android {
    namespace = "yz.l.compose.feature.common"
}

dependencies {
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(projects.core.network)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.room.paging)
}