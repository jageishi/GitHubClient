package org.ageage.githubclient.data.network.impl

import org.ageage.githubclient.data.network.SearchApiClient
import org.ageage.githubclient.data.network.response.RepositoryResponse
import org.ageage.githubclient.data.network.service.GitHubApiService

internal class SearchApiClientImpl(
    private val gitHubApiService: GitHubApiService
) : SearchApiClient {
    override suspend fun searchRepositories(keyword: String): RepositoryResponse {
        gitHubApiService.searchRepositories("$keyword+in:name") // TODO  詰替えを行う
        return RepositoryResponse(emptyList())
    }
}
