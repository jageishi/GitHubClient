package org.ageage.githubclient.data.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.ageage.githubclient.data.api.GitHubApiClient
import org.ageage.githubclient.data.repository.GitHubRepository
import org.ageage.githubclient.data.repository.impl.GitHubRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
internal object SearchRepositoryModule {

    @Provides
    fun providesSearchRepository(gitHubApiClient: GitHubApiClient): GitHubRepository {
        return GitHubRepositoryImpl(gitHubApiClient)
    }
}