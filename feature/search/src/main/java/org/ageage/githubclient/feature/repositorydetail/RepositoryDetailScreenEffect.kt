package org.ageage.githubclient.feature.repositorydetail

internal sealed interface RepositoryDetailScreenEffect {

    data object NavigateUp : RepositoryDetailScreenEffect
}