package org.ageage.githubclient.data.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.ageage.githubclient.data.network.SearchApiClient
import org.ageage.githubclient.data.repository.SearchRepository
import org.ageage.githubclient.data.repository.impl.SearchRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SearchRepositoryModule {

    @Provides
    @Singleton
    fun providesSearchRepository(searchApiClient: SearchApiClient): SearchRepository {
        return SearchRepositoryImpl(searchApiClient)
    }
}