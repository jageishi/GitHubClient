package org.ageage.githubclient.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor() : ViewModel() {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    val effect = effectChannel.receiveAsFlow()

    fun onButtonClick() = viewModelScope.launch {
        effectChannel.send(Effect.OnButtonClick)
    }

    sealed interface Effect {
        object OnButtonClick : Effect
    }
}