package org.ageage.githubclient.feature.repositorydetail

internal sealed interface RepositoryDetailScreenEvent {

    data object OnTopAppBarBackArrowClick : RepositoryDetailScreenEvent

    data object OnApiErrorDialogDismissRequest : RepositoryDetailScreenEvent
}