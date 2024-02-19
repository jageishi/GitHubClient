package org.ageage.githubclient.feature.searchrepository

internal sealed interface SearchRepositoryScreenEvent {

    data class OnCreate(val searchQuery: String) : SearchRepositoryScreenEvent

    data object OnTopAppBarBackArrowClick : SearchRepositoryScreenEvent
}