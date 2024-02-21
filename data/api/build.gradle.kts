plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "org.ageage.githubclient.data.api"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
}