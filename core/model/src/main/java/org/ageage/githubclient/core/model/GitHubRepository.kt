package org.ageage.githubclient.core.model

import androidx.compose.runtime.Stable

@Stable
data class GitHubRepository(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val stargazersCount: Int,
    val topics: List<String>,
    val owner: Owner
)