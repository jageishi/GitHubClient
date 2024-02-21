package org.ageage.githubclient.feature.searchrepository

internal sealed interface SearchRepositoryScreenEvent {

    data class OnCreate(val searchQuery: String) : SearchRepositoryScreenEvent

    data object OnTopAppBarBackArrowClick : SearchRepositoryScreenEvent

    data class OnGitHubRepositoryDetailCardClick(
        val repoName: String,
        val ownerName: String
    ) : SearchRepositoryScreenEvent

    data object OnApiErrorDialogDismissRequest : SearchRepositoryScreenEvent
}