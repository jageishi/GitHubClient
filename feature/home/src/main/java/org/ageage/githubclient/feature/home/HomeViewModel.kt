package org.ageage.githubclient.feature.home

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
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

    private val effectChannel = Channel<HomeScreenEffect>(Channel.UNLIMITED)
    val effect = effectChannel.receiveAsFlow()

    fun onEvent(event: HomeScreenEvent) = viewModelScope.launch {
        when (event) {
            is HomeScreenEvent.OnSearchQueryChange -> {
                _uiState.update {
                    it.copy(
                        searchQuery = event.query,
                        shouldShowEmptyQueryErrorText = event.query.isBlank()
                    )
                }
            }

            HomeScreenEvent.OnKeyboardActionSearch, // TODO 空文字で発火するとクラッシュする問題を修正する
            HomeScreenEvent.OnSearchButtonClick -> {
                val searchQuery = uiState.value.searchQuery.trim()
                if (searchQuery.isBlank()) {
                    _uiState.update { it.copy(shouldShowEmptyQueryErrorText = true) }
                } else {
                    effectChannel.send(
                        HomeScreenEffect.NavigateToSearchRepositoryScreen(
                            query = uiState.value.searchQuery.trim()
                        )
                    )
                }

            }
        }
    }
}