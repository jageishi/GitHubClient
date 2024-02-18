plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
}

android {
    namespace = "org.ageage.githubclient.core.model"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}