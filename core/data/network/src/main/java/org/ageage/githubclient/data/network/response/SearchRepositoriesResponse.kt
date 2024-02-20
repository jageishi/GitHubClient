package org.ageage.githubclient.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRepositoriesResponse(
    @SerialName("items") val items: List<SearchRepositoriesResponseItem>
)

@Serializable
data class SearchRepositoriesResponseItem(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("description") val description: String?,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("topics") val topics: List<String>,
    @SerialName("owner") val owner: SearchRepositoriesResponseItemOwner,
)

@Serializable
data class SearchRepositoriesResponseItemOwner(
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatarUrl: String
)