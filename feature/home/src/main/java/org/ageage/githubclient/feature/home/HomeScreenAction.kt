package org.ageage.githubclient.feature.home

internal sealed interface HomeScreenAction {

    data class UpdateQuery(val query: String) : HomeScreenAction

    data object ShowEmptyQueryErrorText : HomeScreenAction
}