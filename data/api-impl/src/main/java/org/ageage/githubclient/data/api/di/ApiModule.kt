package org.ageage.githubclient.data.api.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.ageage.githubclient.data.api.GitHubApiClient
import org.ageage.githubclient.data.api.impl.ApiExceptionMapper
import org.ageage.githubclient.data.api.impl.ApiExceptionMapperImpl
import org.ageage.githubclient.data.api.impl.GitHubApiClientImpl
import org.ageage.githubclient.data.api.interceptor.AccessTokenInterceptor
import org.ageage.githubclient.data.api.service.GitHubApiService
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideAccessTokenInterceptor(): AccessTokenInterceptor {
        return AccessTokenInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(accessTokenInterceptor: AccessTokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY

            })
            .addInterceptor(accessTokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideGitHubApiService(retrofit: Retrofit): GitHubApiService {
        return retrofit.create(GitHubApiService::class.java)
    }

    @Provides
    fun providesSearchApiClient(
        gitHubApiService: GitHubApiService,
        apiExceptionMapper: ApiExceptionMapper
    ): GitHubApiClient {
        return GitHubApiClientImpl(gitHubApiService, apiExceptionMapper)
    }

    @Provides
    fun provideApiExceptionMapper(): ApiExceptionMapper {
        return ApiExceptionMapperImpl()
    }
}