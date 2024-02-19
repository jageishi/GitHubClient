package org.ageage.githubclient.feature.searchrepository

internal sealed interface SearchRepositoryScreenEffect {

    data object NavigateUp : SearchRepositoryScreenEffect
}