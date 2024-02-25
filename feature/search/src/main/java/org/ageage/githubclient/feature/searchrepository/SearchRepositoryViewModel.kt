package org.ageage.githubclient.feature.searchrepository

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.ageage.githubclient.common.exception.ApiException
import org.ageage.githubclient.core.ui.screenconfig.SearchRepositoryScreenConfig.getSearchRepositoryScreenQueryArg
import org.ageage.githubclient.core.ui.util.ApiErrorStateHelper
import org.ageage.githubclient.data.repository.GitHubRepository
import javax.inject.Inject

@HiltViewModel
internal class SearchRepositoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val reducer: SearchRepositoryReducer,
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    val uiState: StateFlow<SearchRepositoryScreenState>
        get() = reducer.state

    private val effectChannel = Channel<SearchRepositoryScreenEffect>(Channel.UNLIMITED)
    val effect = effectChannel.receiveAsFlow()

    val apiErrorStateHelper = ApiErrorStateHelper()

    fun onEvent(event: SearchRepositoryScreenEvent) = viewModelScope.launch {
        when (event) {
            SearchRepositoryScreenEvent.OnInitializeRequest -> {
                try {
                    val query = savedStateHandle.getSearchRepositoryScreenQueryArg()
                    reducer.dispatch(SearchRepositoryScreenAction.Initialize(query))
                    reducer.dispatch(SearchRepositoryScreenAction.BeginLoading)
                    val gitHubRepositories = gitHubRepository.searchRepositories(query)
                    reducer.dispatch(SearchRepositoryScreenAction.SetGitHubRepos(gitHubRepositories))
                } catch (e: ApiException) {
                    apiErrorStateHelper.handleApiException(e)
                } finally {
                    reducer.dispatch(SearchRepositoryScreenAction.EndLoading)
                }
            }

            SearchRepositoryScreenEvent.OnTopAppBarBackArrowClick -> {
                effectChannel.send(SearchRepositoryScreenEffect.NavigateUp)
            }

            is SearchRepositoryScreenEvent.OnGitHubRepositoryDetailCardClick -> {
                effectChannel.send(
                    SearchRepositoryScreenEffect.NavigateToRepositoryDetailScreen(
                        owner = event.owner,
                        repo = event.repo
                    )
                )
            }

            SearchRepositoryScreenEvent.OnApiErrorDialogDismissRequest -> {
                apiErrorStateHelper.clearErrorState()
                effectChannel.send(SearchRepositoryScreenEffect.NavigateUp)
            }
        }
    }
}