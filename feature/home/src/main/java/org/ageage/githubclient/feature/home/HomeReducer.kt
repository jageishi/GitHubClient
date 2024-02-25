package org.ageage.githubclient.feature.home

import org.ageage.githubclient.core.ui.reducer.BaseReducer
import javax.inject.Inject

internal class HomeReducer @Inject constructor() :
    BaseReducer<HomeScreenState, HomeScreenAction>(HomeScreenState()) {

    override fun reduce(state: HomeScreenState, action: HomeScreenAction): HomeScreenState {
        return when (action) {
            is HomeScreenAction.UpdateQuery -> {
                state.copy(
                    searchQuery = action.query,
                    shouldShowEmptyQueryErrorText = action.query.isBlank()
                )
            }

            is HomeScreenAction.ShowEmptyQueryErrorText -> {
                state.copy(shouldShowEmptyQueryErrorText = true)
            }
        }
    }
}