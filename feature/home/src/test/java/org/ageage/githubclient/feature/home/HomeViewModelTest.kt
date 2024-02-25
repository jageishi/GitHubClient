package org.ageage.githubclient.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        viewModel = HomeViewModel()
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onSearchQueryChange_whenQueryIsNotEmpty() = runTest {
        val uiStateList = mutableListOf<HomeScreenState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {
                uiStateList.add(it)
            }
        }

        viewModel.onEvent(HomeScreenEvent.OnSearchQueryChange("query"))

        assertEquals(2, uiStateList.size)
        assertEquals(
            listOf(
                HomeScreenState(),
                HomeScreenState(searchQuery = "query", shouldShowEmptyQueryErrorText = false)
            ),
            uiStateList
        )

        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onSearchQueryChange_whenQueryIsEmpty() = runTest {
        val uiStateList = mutableListOf<HomeScreenState>()
        val uiStateCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {
                uiStateList.add(it)
            }
        }

        viewModel.onEvent(HomeScreenEvent.OnSearchQueryChange(""))

        assertEquals(2, uiStateList.size)
        assertEquals(
            listOf(
                HomeScreenState(),
                HomeScreenState(shouldShowEmptyQueryErrorText = true)
            ),
            uiStateList
        )

        uiStateCollectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onKeyboardActionSearch_whenQueryIsNotEmpty() = runTest {
        val effectList = mutableListOf<HomeScreenEffect>()
        val effectCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect {
                effectList.add(it)
            }
        }

        viewModel.onEvent(HomeScreenEvent.OnKeyboardActionSearch("query"))

        assertEquals(1, effectList.size)
        assertEquals(
            HomeScreenEffect.NavigateToSearchRepositoryScreen("query"),
            effectList[0]
        )

        effectCollectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onKeyboardActionSearch_whenQueryIsEmpty() = runTest {
        val uiStateList = mutableListOf<HomeScreenState>()
        val uiStateCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {
                uiStateList.add(it)
            }
        }
        val effectList = mutableListOf<HomeScreenEffect>()
        val effectCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect {
                effectList.add(it)
            }
        }

        viewModel.onEvent(HomeScreenEvent.OnKeyboardActionSearch(""))

        assertEquals(2, uiStateList.size)
        assertEquals(
            listOf(
                HomeScreenState(),
                HomeScreenState(shouldShowEmptyQueryErrorText = true)
            ),
            uiStateList
        )
        assertEquals(0, effectList.size)

        uiStateCollectJob.cancel()
        effectCollectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onSearchButtonClick_whenQueryIsNotEmpty() = runTest {
        val effectList = mutableListOf<HomeScreenEffect>()
        val effectCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect {
                effectList.add(it)
            }
        }

        viewModel.onEvent(HomeScreenEvent.OnSearchButtonClick("query"))

        assertEquals(1, effectList.size)
        assertEquals(
            HomeScreenEffect.NavigateToSearchRepositoryScreen("query"),
            effectList[0]
        )

        effectCollectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onSearchButtonClick_whenQueryIsEmpty() = runTest {
        val uiStateList = mutableListOf<HomeScreenState>()
        val uiStateCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {
                uiStateList.add(it)
            }
        }
        val effectList = mutableListOf<HomeScreenEffect>()
        val effectCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect {
                effectList.add(it)
            }
        }

        viewModel.onEvent(HomeScreenEvent.OnSearchButtonClick(""))

        assertEquals(2, uiStateList.size)
        assertEquals(
            listOf(
                HomeScreenState(),
                HomeScreenState(shouldShowEmptyQueryErrorText = true)
            ),
            uiStateList
        )
        assertEquals(0, effectList.size)

        uiStateCollectJob.cancel()
        effectCollectJob.cancel()
    }
}