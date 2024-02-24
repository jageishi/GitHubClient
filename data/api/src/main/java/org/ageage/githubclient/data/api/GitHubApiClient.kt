package org.ageage.githubclient.data.api

import org.ageage.githubclient.data.api.response.GetRepositoriesResponse
import org.ageage.githubclient.data.api.response.GetRepositoryResponse

interface GitHubApiClient {

    suspend fun getRepositories(keyword: String): GetRepositoriesResponse

    suspend fun getRepository(owner: String, repo: String): GetRepositoryResponse
}