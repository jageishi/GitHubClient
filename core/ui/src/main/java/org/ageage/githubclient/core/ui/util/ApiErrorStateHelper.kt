package org.ageage.githubclient.core.ui.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ageage.githubclient.exception.ApiException
import org.ageage.githubclient.exception.ForbiddenException
import org.ageage.githubclient.exception.NetworkException
import org.ageage.githubclient.exception.ServerErrorException
import org.ageage.githubclient.exception.UnexpectedException

sealed interface ApiErrorState {

    data object None : ApiErrorState

    data object NetworkError : ApiErrorState

    data object ForbiddenError : ApiErrorState

    data object ServerError : ApiErrorState

    data object UnexpectedError : ApiErrorState
}

class ApiErrorStateHelper {

    private val _errorState = MutableStateFlow<ApiErrorState>(ApiErrorState.None)
    val errorState: StateFlow<ApiErrorState> = _errorState.asStateFlow()

    fun handleApiException(exception: ApiException) {
        _errorState.value = when (exception) {
            is NetworkException -> ApiErrorState.NetworkError
            is ForbiddenException -> ApiErrorState.ForbiddenError
            is ServerErrorException -> ApiErrorState.ServerError
            is UnexpectedException -> ApiErrorState.UnexpectedError
        }
    }

    fun clearErrorState() {
        _errorState.value = ApiErrorState.None
    }
}