package org.ageage.githubclient.data.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRepositoriesResponse(
    @SerialName("items") val items: List<GetRepositoriesResponseItem>
)

@Serializable
data class GetRepositoriesResponseItem(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("description") val description: String?,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("topics") val topics: List<String>,
    @SerialName("owner") val owner: GetRepositoriesResponseItemOwner,
)

@Serializable
data class GetRepositoriesResponseItemOwner(
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatarUrl: String
)