package org.ageage.githubclient.core.ui.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseReducer<STATE, ACTION>(initialState: STATE) {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    fun dispatch(action: ACTION) {
        _state.update { reduce(it, action) }
    }

    abstract fun reduce(state: STATE, action: ACTION): STATE
}