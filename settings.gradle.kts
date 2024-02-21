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
include(":data:repository")
include(":data:repository-impl")
include(":data:api")
include(":data:api-impl")
include(":feature:home")
include(":feature:search")
include(":core:exception")
