package org.ageage.githubclient.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.ageage.githubclient.data.network.SearchApiClient
import org.ageage.githubclient.data.network.impl.SearchApiClientImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SearchApiClientModule {

    @Provides
    @Singleton
    fun providesSearchApiClient(): SearchApiClient {
        return SearchApiClientImpl()
    }
}