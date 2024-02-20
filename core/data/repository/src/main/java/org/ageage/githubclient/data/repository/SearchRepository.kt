package org.ageage.githubclient.data.repository

import org.ageage.githubclient.core.model.GitHubRepository

interface SearchRepository {

    suspend fun searchRepositories(keyword: String): List<GitHubRepository>
}