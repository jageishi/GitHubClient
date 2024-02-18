package org.ageage.githubclient.data.network.service

import retrofit2.http.GET
import retrofit2.http.Query

internal interface GitHubApiService {

    @GET("/search/repositories")
    suspend fun searchRepositories(
        @Query("q") keyword: String,
        @Query("sort") sort: String = "stars"
    )
}