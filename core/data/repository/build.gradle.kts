plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
}

android {
    namespace = "org.ageage.githubclient.data.repository"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.androidx.core.ktx)
}