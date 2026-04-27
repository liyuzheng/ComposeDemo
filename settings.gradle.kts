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
        maven {
            url = uri("${rootDir}/local-repo")
            println("current maven url is $url")
        }
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()

    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("${rootDir}/local-repo")
            println("current maven url is $url")
        }
        mavenLocal()
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
include(":feature:main")
include(":feature:main:impl")
include(":feature:main:api")
include(":feature:discover")
include(":feature:discover:api")
include(":feature:discover:impl")
include(":feature:discover:data")
include(":feature:game")
include(":feature:game:api")
include(":feature:game:impl")
include(":feature:game:data")
include(":feature:home")
include(":feature:home:api")
include(":feature:home:data")
include(":feature:home:impl")
