package org.ageage.githubclient.feature.home

import org.junit.Test
import kotlin.test.assertEquals

internal class HomeScreenStateTest {

    @Test
    fun defaultState() {
        val state = HomeScreenState()

        assertEquals(
            HomeScreenState(
                searchQuery = "",
                shouldShowEmptyQueryErrorText = false
            ),
            state
        )
    }
}