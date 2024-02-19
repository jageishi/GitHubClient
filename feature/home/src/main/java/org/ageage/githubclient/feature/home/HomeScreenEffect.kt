package org.ageage.githubclient.feature.home


internal sealed interface HomeScreenEffect {

    data class NavigateToSearchRepositoryScreen(val query: String) : HomeScreenEffect
}
