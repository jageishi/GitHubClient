package org.ageage.githubclient.feature.home

internal sealed interface HomeScreenEvent {

    data class OnSearchButtonClick(val query: String) : HomeScreenEvent

    data class OnKeyboardActionSearch(val query: String) : HomeScreenEvent

    data class OnSearchQueryChange(val query: String) : HomeScreenEvent
}
