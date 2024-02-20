package org.ageage.githubclient.feature.searchrepository

import androidx.compose.runtime.Stable
import org.ageage.githubclient.core.model.GitHubRepository

@Stable
internal data class SearchRepositoryScreenState(
    val searchQuery: String = "",
    val gitHubRepositories: List<GitHubRepository> = emptyList(),
)