package org.ageage.githubclient.feature.searchrepository

import org.ageage.githubclient.core.ui.reducer.BaseReducer
import javax.inject.Inject

internal class SearchRepositoryReducer @Inject constructor() :
    BaseReducer<SearchRepositoryScreenState, SearchRepositoryScreenAction>(
        SearchRepositoryScreenState()
    ) {

    override fun reduce(
        state: SearchRepositoryScreenState,
        action: SearchRepositoryScreenAction
    ): SearchRepositoryScreenState {
        when (action) {
            is SearchRepositoryScreenAction.Initialize -> {
                return state.copy(
                    isInitialized = true,
                    searchQuery = action.query
                )
            }

            is SearchRepositoryScreenAction.BeginLoading -> {
                return state.copy(isLoading = true)
            }

            is SearchRepositoryScreenAction.EndLoading -> {
                return state.copy(isLoading = false)
            }

            is SearchRepositoryScreenAction.SetGitHubRepos -> {
                return state.copy(gitHubRepos = action.repos)
            }
        }
    }
}