package org.ageage.githubclient.feature.searchrepository

import androidx.compose.runtime.Stable
import org.ageage.githubclient.core.model.GitHubRepo

@Stable
internal data class SearchRepositoryScreenState(
    val isInitialized: Boolean = false,
    val searchQuery: String = "",
    val gitHubRepos: List<GitHubRepo> = emptyList(),
    val isLoading: Boolean = false
)