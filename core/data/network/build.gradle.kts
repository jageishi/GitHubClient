plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
}

android {
    namespace = "org.ageage.githubclient.data.network"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}