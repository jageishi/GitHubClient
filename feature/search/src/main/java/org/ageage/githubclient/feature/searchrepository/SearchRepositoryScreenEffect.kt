package org.ageage.githubclient.feature.searchrepository

internal sealed interface SearchRepositoryScreenEffect {

    data class NavigateToRepositoryDetailScreen(
        val owner: String,
        val repo: String
    ) : SearchRepositoryScreenEffect

    data object NavigateUp : SearchRepositoryScreenEffect
}