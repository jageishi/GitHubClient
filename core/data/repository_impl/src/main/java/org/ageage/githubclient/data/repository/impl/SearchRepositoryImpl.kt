package org.ageage.githubclient.data.repository.impl

import org.ageage.githubclient.core.model.GitHubRepository
import org.ageage.githubclient.core.model.Owner
import org.ageage.githubclient.data.network.SearchApiClient
import org.ageage.githubclient.data.repository.SearchRepository

internal class SearchRepositoryImpl(
    private val searchApiClient: SearchApiClient
) : SearchRepository {
    override suspend fun searchRepositories(keyword: String): List<GitHubRepository> {
        return searchApiClient.searchRepositories(keyword).items.map {
            GitHubRepository(
                id = it.id,
                name = it.name,
                fullName = it.fullName,
                description = it.description,
                stargazersCount = it.stargazersCount,
                topics = it.topics,
                owner = Owner(
                    name = it.owner.login,
                    avatarUrl = it.owner.avatarUrl
                )
            )
        }
    }
}