import dsl.android
import dsl.kotlinOptions
import dsl.library
import dsl.libs
import dsl.testImplementation
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
            }
            android {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_11.toString()
                }
            }

            dependencies {
                add("testImplementation", kotlin("test"))
                testImplementation(libs.library("mockk"))
                testImplementation(libs.library("junit"))
                testImplementation(libs.library("kotlinx-coroutines-test"))
            }
        }
    }
}