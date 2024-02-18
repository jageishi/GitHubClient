plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
}

android {
    namespace = "org.ageage.githubclient.date.repository"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}