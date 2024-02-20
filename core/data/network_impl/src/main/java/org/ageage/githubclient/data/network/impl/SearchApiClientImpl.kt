package org.ageage.githubclient.data.network.impl

import org.ageage.githubclient.data.network.SearchApiClient
import org.ageage.githubclient.data.network.response.SearchRepositoriesResponse
import org.ageage.githubclient.data.network.service.GitHubApiService

internal class SearchApiClientImpl(
    private val gitHubApiService: GitHubApiService
) : SearchApiClient {
    override suspend fun searchRepositories(keyword: String): SearchRepositoriesResponse {
        return gitHubApiService.searchRepositories(keyword)
    }
}