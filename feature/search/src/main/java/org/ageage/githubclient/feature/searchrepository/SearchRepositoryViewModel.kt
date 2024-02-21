package org.ageage.githubclient.feature.searchrepository

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
import org.ageage.githubclient.core.ui.util.ApiErrorStateHelper
import org.ageage.githubclient.data.repository.SearchRepository
import org.ageage.githubclient.exception.ApiException
import javax.inject.Inject

@HiltViewModel
internal class SearchRepositoryViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchRepositoryScreenState())
    val uiState: StateFlow<SearchRepositoryScreenState> = _uiState.asStateFlow()

    private val effectChannel = Channel<SearchRepositoryScreenEffect>(Channel.UNLIMITED)
    val effect = effectChannel.receiveAsFlow()

    val apiErrorStateHelper = ApiErrorStateHelper()

    fun onEvent(event: SearchRepositoryScreenEvent) = viewModelScope.launch {
        when (event) {
            is SearchRepositoryScreenEvent.OnCreate -> {
                try {
                    _uiState.update { it.copy(searchQuery = event.searchQuery) }
                    // TODO 検索処理の呼び出し場所変更
                    // TODO 読込中の表示
                    // TODO ページネーション
                    val gitHubRepositories = searchRepository.searchRepositories(event.searchQuery)
                    _uiState.update { it.copy(gitHubRepositories = gitHubRepositories) }
                } catch (e: ApiException) {
                    apiErrorStateHelper.handleApiException(e)
                }
            }

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