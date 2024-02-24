package org.ageage.githubclient.data.repository

import org.ageage.githubclient.core.model.GitHubRepo

interface GitHubRepository {

    suspend fun searchRepositories(keyword: String): List<GitHubRepo>

    suspend fun showRepository(owner: String, repo: String): GitHubRepo
}