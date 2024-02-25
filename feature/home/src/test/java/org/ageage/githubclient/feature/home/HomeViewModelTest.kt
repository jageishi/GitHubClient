package org.ageage.githubclient.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


internal class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var reducer: HomeReducer

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = HomeViewModel(
            reducer = reducer
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onSearchQueryChange() = runTest {
        val query = "query"
        viewModel.onEvent(HomeScreenEvent.OnSearchQueryChange(query))

        verifySequence {
            reducer.dispatch(HomeScreenAction.UpdateQuery(query))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onKeyboardActionSearch_whenQueryIsNotEmpty() = runTest {
        val query = "query"
        every {
            reducer.state
        } returns MutableStateFlow(HomeScreenState(searchQuery = query))

        viewModel.onEvent(HomeScreenEvent.OnKeyboardActionSearch)

        assertEquals(
            HomeScreenEffect.NavigateToSearchRepositoryScreen(query),
            viewModel.effect.first()
        )
    }

    @Test
    fun onKeyboardActionSearch_whenQueryIsEmpty() = runTest {
        every {
            reducer.state
        } returns MutableStateFlow(HomeScreenState(searchQuery = ""))

        viewModel.onEvent(HomeScreenEvent.OnKeyboardActionSearch)

        verifySequence {
            reducer.state
            reducer.dispatch(HomeScreenAction.ShowEmptyQueryErrorText)
        }
    }

    @Test
    fun onSearchButtonClick_whenQueryIsNotEmpty() = runTest {
        val query = "query"
        every {
            reducer.state
        } returns MutableStateFlow(HomeScreenState(searchQuery = query))

        viewModel.onEvent(HomeScreenEvent.OnSearchButtonClick)

        assertEquals(
            HomeScreenEffect.NavigateToSearchRepositoryScreen(query),
            viewModel.effect.first()
        )
    }

    @Test
    fun onSearchButtonClick_whenQueryIsEmpty() = runTest {
        every {
            reducer.state
        } returns MutableStateFlow(HomeScreenState(searchQuery = ""))

        viewModel.onEvent(HomeScreenEvent.OnSearchButtonClick)

        verifySequence {
            reducer.state
            reducer.dispatch(HomeScreenAction.ShowEmptyQueryErrorText)
        }
    }
}