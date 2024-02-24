package org.ageage.githubclient.feature.repositorydetail

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
import org.ageage.githubclient.core.ui.screenconfig.RepositoryDetailScreenConfig.getRepositoryDetailScreenOwnerArg
import org.ageage.githubclient.core.ui.screenconfig.RepositoryDetailScreenConfig.getRepositoryDetailScreenRepoArg
import org.ageage.githubclient.core.ui.util.ApiErrorStateHelper
import org.ageage.githubclient.data.repository.GitHubRepository
import javax.inject.Inject

@HiltViewModel
internal class RepositoryDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepositoryDetailScreenState())
    val uiState: StateFlow<RepositoryDetailScreenState> = _uiState.asStateFlow()

    private val effectChannel = Channel<RepositoryDetailScreenEffect>(Channel.UNLIMITED)
    val effect = effectChannel.receiveAsFlow()

    val apiErrorStateHelper = ApiErrorStateHelper()

    init {
        viewModelScope.launch {
            try {
                val owner = savedStateHandle.getRepositoryDetailScreenOwnerArg()
                val repo = savedStateHandle.getRepositoryDetailScreenRepoArg()
                _uiState.update { it.copy(owner = owner, repo = repo, isLoading = true) }
                val gitHubRepo = gitHubRepository.showRepository(owner = owner, repo = repo)
                _uiState.update { it.copy(gitHubRepo = gitHubRepo) }
            } catch (e: ApiException) {
                apiErrorStateHelper.handleApiException(e)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onEvent(event: RepositoryDetailScreenEvent) = viewModelScope.launch {
        when (event) {
            RepositoryDetailScreenEvent.OnTopAppBarBackArrowClick -> {
                effectChannel.send(RepositoryDetailScreenEffect.NavigateUp)
            }

            RepositoryDetailScreenEvent.OnApiErrorDialogDismissRequest -> TODO()
        }
    }
}