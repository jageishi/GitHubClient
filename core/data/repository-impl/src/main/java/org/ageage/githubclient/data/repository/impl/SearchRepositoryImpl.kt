package org.ageage.githubclient.data.repository.impl

import org.ageage.githubclient.core.model.Repository
import org.ageage.githubclient.data.network.SearchApiClient
import org.ageage.githubclient.data.repository.SearchRepository

internal class SearchRepositoryImpl(
    private val searchApiClient: SearchApiClient
) : SearchRepository {
    override suspend fun searchRepositories(keyword: String): List<Repository> {
        return searchApiClient.searchRepositories(keyword).repositories.map {
            Repository(it)
        }
    }
}