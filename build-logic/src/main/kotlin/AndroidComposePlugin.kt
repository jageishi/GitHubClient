import dsl.android
import dsl.androidTestImplementation
import dsl.debugImplementation
import dsl.implementation
import dsl.library
import dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            android {
                buildFeatures.compose = true
                composeOptions {
                    kotlinCompilerExtensionVersion = AppConfig.COMPOSE_COMPILER_VERSION
                }
            }
            dependencies {
                implementation(platform(libs.library("androidx-compose-bom")))
                implementation(libs.library("androidx-compose-ui"))
                implementation(libs.library("androidx-compose-ui-graphics"))
                implementation(libs.library("androidx-compose-ui-tooling-preview"))
                implementation(libs.library("androidx-compose-material3"))
                implementation(libs.library("androidx-activity-compose"))
                implementation(libs.library("androidx-lifecycle-runtime-compose"))
                debugImplementation(libs.library("androidx-compose-ui-tooling"))
                debugImplementation(libs.library("androidx-compose-ui-test-manifest"))
                androidTestImplementation(platform(libs.library("androidx-compose-bom")))
                androidTestImplementation(libs.library("androidx-compose-ui-test-junit4"))
            }
        }
    }
}