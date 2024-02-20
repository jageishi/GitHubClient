package org.ageage.githubclient.core.model

import androidx.compose.runtime.Stable

@Stable
data class Owner(
    val name: String,
    val avatarUrl: String
)