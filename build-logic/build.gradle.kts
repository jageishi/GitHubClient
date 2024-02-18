plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.githubclient.android.application.get().pluginId
            implementationClass = "AndroidApplicationPlugin"

        }
        register("androidLibrary") {
            id = libs.plugins.githubclient.android.library.get().pluginId
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidKotlin") {
            id = libs.plugins.githubclient.android.kotlin.get().pluginId
            implementationClass = "AndroidKotlinPlugin"
        }
        register("androidCompose") {
            id = libs.plugins.githubclient.android.compose.get().pluginId
            implementationClass = "AndroidComposePlugin"
        }
    }
}