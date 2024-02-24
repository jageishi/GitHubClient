package org.ageage.githubclient.data.api.service

import org.ageage.githubclient.data.api.response.GetRepositoriesResponse
import org.ageage.githubclient.data.api.response.GetRepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GitHubApiService {

    @GET("/search/repositories")
    suspend fun getRepositories(
        @Query("q") keyword: String,
        @Query("sort") sort: String = "stars"
    ): GetRepositoriesResponse

    @GET("/repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): GetRepositoryResponse
}