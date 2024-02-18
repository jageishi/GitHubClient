plugins {
    alias(libs.plugins.githubclient.android.library)
    alias(libs.plugins.githubclient.android.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "org.ageage.githubclient.data.network.impl"
}

dependencies {
    implementation(projects.data.network)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    implementation(libs.okhttp)
    debugImplementation(libs.okhttp.logging)

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization.converter)
}