package org.ageage.githubclient.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ageage.githubclient.data.repository.SearchRepository
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState

    private val effectChannel = Channel<HomeScreenEffect>(Channel.UNLIMITED)
    val effect = effectChannel.receiveAsFlow()

    fun onEvent(event: HomeScreenEvent) = viewModelScope.launch {
        when (event) {
            is HomeScreenEvent.OnSearchQueryChange -> {
                _uiState.update { it.copy(searchQuery = event.query) }
            }

            HomeScreenEvent.OnKeyboardActionSearch,
            HomeScreenEvent.OnSearchButtonClick -> {
                effectChannel.send(HomeScreenEffect.NavigateToSearchRepositories)
            }
        }
    }
}