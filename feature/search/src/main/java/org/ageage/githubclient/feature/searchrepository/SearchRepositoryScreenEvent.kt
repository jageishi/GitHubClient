package org.ageage.githubclient.feature.searchrepository

internal sealed interface SearchRepositoryScreenEvent {

    data object OnTopAppBarBackArrowClick : SearchRepositoryScreenEvent

    data class OnGitHubRepositoryDetailCardClick(
        val owner: String,
        val repo: String
    ) : SearchRepositoryScreenEvent

    data object OnApiErrorDialogDismissRequest : SearchRepositoryScreenEvent
}