plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
    alias(libs.plugins.githubclient.android.compose)
}

android {
    namespace = "org.ageage.githubclient.core.model"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}