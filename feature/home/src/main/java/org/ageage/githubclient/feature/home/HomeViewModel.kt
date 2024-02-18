package org.ageage.githubclient.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.ageage.githubclient.data.repository.SearchRepository
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    val effect = effectChannel.receiveAsFlow()

    fun onButtonClick() = viewModelScope.launch {
        searchRepository.searchRepositories("keyword").let {
            Log.d("HomeViewModel", "onButtonClick: $it")
        }
        effectChannel.send(Effect.OnButtonClick)
    }

    sealed interface Effect {
        object OnButtonClick : Effect
    }
}