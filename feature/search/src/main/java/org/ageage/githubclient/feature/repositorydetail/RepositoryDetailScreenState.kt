package org.ageage.githubclient.feature.repositorydetail

import org.ageage.githubclient.core.model.GitHubRepo


internal data class RepositoryDetailScreenState(
    val owner: String = "",
    val repo: String = "",
    val gitHubRepo: GitHubRepo? = null,
    val isLoading: Boolean = false
)