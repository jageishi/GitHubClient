package org.ageage.githubclient.data.api

import org.ageage.githubclient.data.api.response.SearchRepositoriesResponse

interface GitHubApiClient {

    suspend fun getRepositories(keyword: String): SearchRepositoriesResponse
}