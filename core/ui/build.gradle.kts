plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
    alias(libs.plugins.githubclient.android.compose)
}

android {
    namespace = "org.ageage.githubclient.core.ui"
}

dependencies {
    implementation(projects.core.exception)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.hilt.navigation.compose)
}