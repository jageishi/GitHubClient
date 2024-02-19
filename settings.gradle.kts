enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
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

rootProject.name = "GitHubClient"

include(":app")
include(":core:ui")
include(":core:model")
include(":core:data:repository")
include(":core:data:repository-impl")
include(":core:data:network")
include(":core:data:network-impl")
include(":feature:home")
