package org.ageage.githubclient.feature.home

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


internal class HomeReducerTest {

    private lateinit var reducer: HomeReducer

    @Before
    fun setUp() {
        reducer = HomeReducer()
    }

    @Test
    fun updateQuery_whenQueryIsNotEmpty() {
        val newState = reducer.reduce(
            HomeScreenState(),
            HomeScreenAction.UpdateQuery("query")
        )

        assertEquals(
            HomeScreenState(
                searchQuery = "query",
                shouldShowEmptyQueryErrorText = false
            ),
            newState
        )
    }

    @Test
    fun updateQuery_whenQueryIsEmpty() {
        val newState = reducer.reduce(
            HomeScreenState(),
            HomeScreenAction.UpdateQuery("")
        )

        assertEquals(
            HomeScreenState(
                searchQuery = "",
                shouldShowEmptyQueryErrorText = true
            ),
            newState
        )
    }

    @Test
    fun showEmptyQueryErrorText() {
        val newState = reducer.reduce(
            HomeScreenState(),
            HomeScreenAction.ShowEmptyQueryErrorText
        )

        assertEquals(
            HomeScreenState(
                shouldShowEmptyQueryErrorText = true
            ),
            newState
        )
    }
}
