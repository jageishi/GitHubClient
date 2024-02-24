package org.ageage.githubclient.feature.searchrepository

internal sealed interface SearchRepositoryScreenEvent {

    data object OnTopAppBarBackArrowClick : SearchRepositoryScreenEvent

    data class OnGitHubRepositoryDetailCardClick(
        val repoName: String,
        val ownerName: String
    ) : SearchRepositoryScreenEvent

    data object OnApiErrorDialogDismissRequest : SearchRepositoryScreenEvent
}