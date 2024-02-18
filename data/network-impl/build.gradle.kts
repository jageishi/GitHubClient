plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "org.ageage.githubclient.data.network.impl"
}

dependencies {
    implementation(projects.data.network)

    implementation(libs.androidx.core.ktx)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}