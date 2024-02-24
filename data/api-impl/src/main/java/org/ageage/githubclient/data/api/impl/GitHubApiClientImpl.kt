package org.ageage.githubclient.data.api.impl

import org.ageage.githubclient.data.api.GitHubApiClient
import org.ageage.githubclient.data.api.response.GetRepositoriesResponse
import org.ageage.githubclient.data.api.response.GetRepositoryResponse
import org.ageage.githubclient.data.api.service.GitHubApiService

internal class GitHubApiClientImpl(
    private val gitHubApiService: GitHubApiService,
    private val apiExceptionMapper: ApiExceptionMapper
) : GitHubApiClient {

    override suspend fun getRepositories(keyword: String): GetRepositoriesResponse {
        try {
            return gitHubApiService.getRepositories(keyword)
        } catch (e: Exception) {
            throw apiExceptionMapper.map(e)
        }
    }

    override suspend fun getRepository(owner: String, repo: String): GetRepositoryResponse {
        try {
            return gitHubApiService.getRepository(owner, repo)
        } catch (e: Exception) {
            throw apiExceptionMapper.map(e)
        }
    }
}