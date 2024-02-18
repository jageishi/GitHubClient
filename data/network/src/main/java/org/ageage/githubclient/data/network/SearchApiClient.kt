package org.ageage.githubclient.data.network

import org.ageage.githubclient.data.network.response.RepositoryResponse

interface SearchApiClient {

    suspend fun searchRepositories(keyword: String): RepositoryResponse
}