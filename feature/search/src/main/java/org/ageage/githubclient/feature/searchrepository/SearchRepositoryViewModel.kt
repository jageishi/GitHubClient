package org.ageage.githubclient.feature.searchrepository

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ageage.githubclient.common.exception.ApiException
import org.ageage.githubclient.core.ui.screenconfig.SearchRepositoryScreenConfig.getSearchRepositoryScreenQueryArg
import org.ageage.githubclient.core.ui.util.ApiErrorStateHelper
import org.ageage.githubclient.data.repository.SearchRepository
import javax.inject.Inject

@HiltViewModel
internal class SearchRepositoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchRepositoryScreenState())
    val uiState: StateFlow<SearchRepositoryScreenState> = _uiState.asStateFlow()

    private val effectChannel = Channel<SearchRepositoryScreenEffect>(Channel.UNLIMITED)
    val effect = effectChannel.receiveAsFlow()

    val apiErrorStateHelper = ApiErrorStateHelper()

    init {
        viewModelScope.launch {
            val query = savedStateHandle.getSearchRepositoryScreenQueryArg()
            _uiState.update { it.copy(searchQuery = query, isLoading = true) }
            try {
                val gitHubRepositories = searchRepository.searchRepositories(query)
                _uiState.update { it.copy(gitHubRepositories = gitHubRepositories) }
            } catch (e: ApiException) {
                apiErrorStateHelper.handleApiException(e)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onEvent(event: SearchRepositoryScreenEvent) = viewModelScope.launch {
        when (event) {
            SearchRepositoryScreenEvent.OnTopAppBarBackArrowClick -> {
                effectChannel.send(SearchRepositoryScreenEffect.NavigateUp)
            }

            is SearchRepositoryScreenEvent.OnGitHubRepositoryDetailCardClick -> {
                //　TODO 詳細画面に遷移する
            }

            SearchRepositoryScreenEvent.OnApiErrorDialogDismissRequest -> {
                apiErrorStateHelper.clearErrorState()
                effectChannel.send(SearchRepositoryScreenEffect.NavigateUp)
            }
        }
    }
}