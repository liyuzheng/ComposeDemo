// 启用类型安全的项目访问器功能
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComposeDemo"
include(":app")
include(":core")
include(":core:core_navigation")
include(":core:core_tool")
include(":core:network")
include(":core:weight")
include(":core:theme")
include(":feature")
include(":feature:login")
include(":feature:login:api")
include(":feature:login:impl")
include(":feature:lottery")
include(":feature:lottery:api")
include(":feature:lottery:impl")
include(":feature:login:data")
include(":feature:common")
include(":feature:home")
include(":feature:home:impl")
include(":feature:home:api")
include(":feature:discover")
include(":feature:discover:api")
include(":feature:discover:impl")
include(":feature:discover:data")
include(":feature:game")
include(":feature:game:api")
include(":feature:game:impl")
include(":feature:game:data")
