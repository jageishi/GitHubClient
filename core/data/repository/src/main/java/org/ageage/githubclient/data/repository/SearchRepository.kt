package org.ageage.githubclient.data.repository

import org.ageage.githubclient.core.model.Repository

interface SearchRepository {

    suspend fun searchRepositories(keyword: String): List<Repository>
}