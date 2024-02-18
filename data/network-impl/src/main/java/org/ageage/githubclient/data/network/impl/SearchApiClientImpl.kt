package org.ageage.githubclient.data.network.impl

import org.ageage.githubclient.data.network.SearchApiClient
import org.ageage.githubclient.data.network.response.RepositoryResponse

class SearchApiClientImpl : SearchApiClient {
    override suspend fun searchRepositories(keyword: String): RepositoryResponse {
        return RepositoryResponse(
            listOf(
                keyword,
                keyword,
                keyword
            )
        )
    }
}