package org.ageage.githubclient.feature.searchrepository

import io.mockk.mockk
import org.ageage.githubclient.core.model.GitHubRepo
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


internal class SearchRepositoryReducerTest {

    private lateinit var reducer: SearchRepositoryReducer

    @Before
    fun setUp() {
        reducer = SearchRepositoryReducer()
    }

    @Test
    fun initialize() {
        val query = "query"
        val newState = reducer.reduce(
            SearchRepositoryScreenState(),
            SearchRepositoryScreenAction.Initialize(query)
        )

        assertEquals(
            SearchRepositoryScreenState(
                isInitialized = true,
                searchQuery = query
            ),
            newState
        )
    }

    @Test
    fun beginLoading() {
        val newState = reducer.reduce(
            SearchRepositoryScreenState(),
            SearchRepositoryScreenAction.BeginLoading
        )

        assertEquals(
            SearchRepositoryScreenState(
                isLoading = true
            ),
            newState
        )
    }

    @Test
    fun endLoading() {
        val newState = reducer.reduce(
            SearchRepositoryScreenState(),
            SearchRepositoryScreenAction.EndLoading
        )

        assertEquals(
            SearchRepositoryScreenState(
                isLoading = false
            ),
            newState
        )
    }

    @Test
    fun setGitHubRepos() {
        val repos = listOf(
            mockk<GitHubRepo>(),
            mockk<GitHubRepo>()
        )
        val newState = reducer.reduce(
            SearchRepositoryScreenState(),
            SearchRepositoryScreenAction.SetGitHubRepos(repos)
        )

        assertEquals(
            SearchRepositoryScreenState(
                gitHubRepos = repos
            ),
            newState
        )
    }
}