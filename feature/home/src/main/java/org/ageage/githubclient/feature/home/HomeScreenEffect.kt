package org.ageage.githubclient.feature.home


internal sealed interface HomeScreenEffect {

    data object NavigateToSearchRepositories : HomeScreenEffect
}
