package org.ageage.githubclient.feature.searchrepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.ageage.githubclient.common.exception.NetworkException
import org.ageage.githubclient.core.model.GitHubRepo
import org.ageage.githubclient.core.ui.screenconfig.SearchRepositoryScreenConfig.getSearchRepositoryScreenQueryArg
import org.ageage.githubclient.core.ui.util.ApiErrorState
import org.ageage.githubclient.data.repository.GitHubRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals


internal class SearchRepositoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchRepositoryViewModel

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var reducer: SearchRepositoryReducer

    @MockK
    private lateinit var gitHubRepository: GitHubRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = SearchRepositoryViewModel(
            savedStateHandle = savedStateHandle,
            reducer = reducer,
            gitHubRepository = gitHubRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onInitializeRequest_whenSuccess() = runTest {
        val query = "query"
        val mockGitHubRepos = listOf(
            mockk<GitHubRepo>(),
            mockk<GitHubRepo>()
        )
        every {
            savedStateHandle.getSearchRepositoryScreenQueryArg()
        } returns query
        coEvery {
            gitHubRepository.searchRepositories(query)
        } returns mockGitHubRepos


        viewModel.onEvent(SearchRepositoryScreenEvent.OnInitializeRequest)

        coVerifySequence {
            savedStateHandle.getSearchRepositoryScreenQueryArg()
            reducer.dispatch(SearchRepositoryScreenAction.Initialize(query))
            reducer.dispatch(SearchRepositoryScreenAction.BeginLoading)
            gitHubRepository.searchRepositories(query)
            reducer.dispatch(SearchRepositoryScreenAction.SetGitHubRepos(mockGitHubRepos))
            reducer.dispatch(SearchRepositoryScreenAction.EndLoading)
        }
    }

    @Test
    fun onInitializeRequest_whenNetworkExceptionThrown() = runTest {
        val query = "query"
        val apiException = NetworkException(IOException())
        every {
            savedStateHandle.getSearchRepositoryScreenQueryArg()
        } returns query
        coEvery {
            gitHubRepository.searchRepositories(query)
        } throws apiException

        viewModel.onEvent(SearchRepositoryScreenEvent.OnInitializeRequest)

        assertEquals(
            ApiErrorState.NetworkError,
            viewModel.apiErrorStateHelper.errorState.value
        )

        coVerifySequence {
            savedStateHandle.getSearchRepositoryScreenQueryArg()
            reducer.dispatch(SearchRepositoryScreenAction.Initialize(query))
            reducer.dispatch(SearchRepositoryScreenAction.BeginLoading)
            gitHubRepository.searchRepositories(query)
            reducer.dispatch(SearchRepositoryScreenAction.EndLoading)
        }
    }

    @Test
    fun onTopAppBarBackArrowClick() = runTest {
        viewModel.onEvent(SearchRepositoryScreenEvent.OnTopAppBarBackArrowClick)

        assertEquals(
            SearchRepositoryScreenEffect.NavigateUp,
            viewModel.effect.first()
        )
    }

    @Test
    fun onGitHubRepositoryDetailCardClick() = runTest {
        val owner = "owner"
        val repo = "repo"
        viewModel.onEvent(
            SearchRepositoryScreenEvent.OnGitHubRepositoryDetailCardClick(
                owner = owner,
                repo = repo
            )
        )

        assertEquals(
            SearchRepositoryScreenEffect.NavigateToRepositoryDetailScreen(owner, repo),
            viewModel.effect.first()
        )
    }

    @Test
    fun onApiErrorDialogDismissRequest() = runTest {
        viewModel.onEvent(SearchRepositoryScreenEvent.OnApiErrorDialogDismissRequest)

        assertEquals(
            SearchRepositoryScreenEffect.NavigateUp,
            viewModel.effect.first()
        )
    }
}