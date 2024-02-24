package org.ageage.githubclient.feature.home

internal data class HomeScreenState(
    val searchQuery: String = "",
    val shouldShowEmptyQueryErrorText: Boolean = false,
)