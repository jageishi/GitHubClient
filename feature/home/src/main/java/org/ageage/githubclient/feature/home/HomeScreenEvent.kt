package org.ageage.githubclient.feature.home

internal sealed interface HomeScreenEvent {

    data object OnSearchButtonClick : HomeScreenEvent

    data object OnKeyboardActionSearch : HomeScreenEvent

    data class OnSearchQueryChange(val query: String) : HomeScreenEvent
}
