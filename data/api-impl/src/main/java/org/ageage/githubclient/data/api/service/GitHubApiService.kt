package org.ageage.githubclient.data.api.service

import org.ageage.githubclient.data.api.response.SearchRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GitHubApiService {

    @GET("/search/repositories")
    suspend fun getRepositories(
        @Query("q") keyword: String,
        @Query("sort") sort: String = "stars"
    ): SearchRepositoriesResponse
}