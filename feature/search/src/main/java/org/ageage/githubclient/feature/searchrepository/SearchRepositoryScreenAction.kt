package org.ageage.githubclient.feature.searchrepository

import org.ageage.githubclient.core.model.GitHubRepo

internal sealed interface SearchRepositoryScreenAction {

    data class Initialize(val query: String) : SearchRepositoryScreenAction

    data object BeginLoading : SearchRepositoryScreenAction

    data object EndLoading : SearchRepositoryScreenAction

    data class SetGitHubRepos(val repos: List<GitHubRepo>) : SearchRepositoryScreenAction
}