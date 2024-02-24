plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
}

android {
    namespace = "org.ageage.githubclient.common"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}