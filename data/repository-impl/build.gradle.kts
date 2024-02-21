plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "org.ageage.data.repository.impl"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.data.repository)
    implementation(projects.data.api)

    implementation(libs.androidx.core.ktx)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}