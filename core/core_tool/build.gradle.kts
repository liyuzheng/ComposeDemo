import java.util.Properties

plugins {
    alias(libs.plugins.compose.demo.android.library.compose)
}

android {
    namespace = "yz.l.core_tool"
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        // 1. 读取 local.properties
        val localProperties = Properties()
        val localFile = rootProject.file("local.properties")
        if (localFile.exists()) {
            localProperties.load(localFile.inputStream())
        }

        // 2. 定义辅助函数：安全获取字符串并自动添加双引号
        fun getLocalProperty(key: String, defaultValue: String = "\"\""): String {
            val value = localProperties.getProperty(key) ?: return defaultValue
            // 如果值为空，返回默认值（已包含引号）
            // 如果值已经包含双引号（用户手动写了），则直接返回；否则加上双引号
            return if (value.startsWith("\"") && value.endsWith("\"")) value else "\"$value\""
        }

        // 3. 注入 BuildConfig 字段（参数类型、字段名、值）
        buildConfigField("String", "clientId", getLocalProperty("id"))
        buildConfigField(
            "String", "secret", getLocalProperty("sec")
        )


        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        // CMake 编译参数
        externalNativeBuild {
            cmake {
                cppFlags("-O3") // 优化 C++ 代码，增加逆向难度
                arguments("-DANDROID_STL=c++_shared")
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.com.google.code.gson.gson)
    implementation(libs.com.tencent.mmkv.static)
    implementation(libs.accompanist.permissions)
    implementation(libs.kotlinx.serialization.json)
    api(libs.timber)
}