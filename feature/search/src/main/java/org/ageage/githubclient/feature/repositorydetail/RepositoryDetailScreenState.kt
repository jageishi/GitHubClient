package org.ageage.githubclient.feature.repositorydetail


internal data class RepositoryDetailScreenState(
    val owner: String = "",
    val repo: String = "",
    val isLoading: Boolean = false
)