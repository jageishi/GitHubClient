package org.ageage.githubclient.data.api.impl

import org.ageage.githubclient.data.api.GitHubApiClient
import org.ageage.githubclient.data.api.response.SearchRepositoriesResponse
import org.ageage.githubclient.data.api.service.GitHubApiService

internal class GitHubApiClientImpl(
    private val gitHubApiService: GitHubApiService,
    private val apiExceptionMapper: ApiExceptionMapper
) : GitHubApiClient {
    override suspend fun getRepositories(keyword: String): SearchRepositoriesResponse {
        try {
            return gitHubApiService.getRepositories(keyword)
        } catch (e: Exception) {
            throw apiExceptionMapper.map(e)
        }
    }
}