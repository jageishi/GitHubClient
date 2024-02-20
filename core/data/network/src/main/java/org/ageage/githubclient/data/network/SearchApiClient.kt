package org.ageage.githubclient.data.network

import org.ageage.githubclient.data.network.response.SearchRepositoriesResponse

interface SearchApiClient {

    suspend fun searchRepositories(keyword: String): SearchRepositoriesResponse
}