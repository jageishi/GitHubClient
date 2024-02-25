package org.ageage.githubclient.feature.searchrepository

import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class SearchRepositoryScreenStateTest {

    @Test
    fun defaultState() {
        val state = SearchRepositoryScreenState()

        assertEquals(
            SearchRepositoryScreenState(
                isInitialized = false,
                searchQuery = "",
                gitHubRepos = emptyList(),
                isLoading = false,
            ),
            state
        )
    }
}