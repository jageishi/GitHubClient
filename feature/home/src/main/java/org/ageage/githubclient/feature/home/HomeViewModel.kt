package org.ageage.githubclient.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val reducer: HomeReducer
) : ViewModel() {

    val uiState: StateFlow<HomeScreenState>
        get() = reducer.state

    private val effectChannel = Channel<HomeScreenEffect>(Channel.UNLIMITED)
    val effect = effectChannel.receiveAsFlow()

    fun onEvent(event: HomeScreenEvent) = viewModelScope.launch {
        when (event) {
            is HomeScreenEvent.OnSearchQueryChange -> {
                reducer.dispatch(HomeScreenAction.UpdateQuery(event.query))
            }

            is HomeScreenEvent.OnKeyboardActionSearch,
            is HomeScreenEvent.OnSearchButtonClick -> {
                val searchQuery = uiState.value.searchQuery
                if (searchQuery.isBlank()) {
                    reducer.dispatch(HomeScreenAction.ShowEmptyQueryErrorText)
                } else {
                    effectChannel.send(
                        HomeScreenEffect.NavigateToSearchRepositoryScreen(
                            query = searchQuery
                        )
                    )
                }
            }
        }
    }
}