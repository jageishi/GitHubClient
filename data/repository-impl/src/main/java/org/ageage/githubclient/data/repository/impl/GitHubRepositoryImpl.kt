package org.ageage.githubclient.data.repository.impl

import org.ageage.githubclient.core.model.GitHubRepo
import org.ageage.githubclient.core.model.Owner
import org.ageage.githubclient.data.api.GitHubApiClient
import org.ageage.githubclient.data.repository.GitHubRepository

internal class GitHubRepositoryImpl(
    private val gitHubApiClient: GitHubApiClient
) : GitHubRepository {
    override suspend fun searchRepositories(keyword: String): List<GitHubRepo> {
        return gitHubApiClient.getRepositories(keyword).items.map {
            GitHubRepo(
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

    override suspend fun showRepository(owner: String, repo: String): GitHubRepo {
        return gitHubApiClient.getRepository(owner, repo).let {
            GitHubRepo(
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