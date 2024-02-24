package org.ageage.githubclient.core.ui.screenconfig

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.ageage.githubclient.common.extension.decode
import org.ageage.githubclient.common.extension.encode

data object SearchRepositoryScreenConfig : ScreenConfig {
    override val route: String = "search_repository"
    private const val queryArg = "query"
    val routeWithArgs = "$route/{$queryArg}"
    val arguments = listOf(
        navArgument(queryArg) {
            type = NavType.StringType
        }
    )

    fun NavController.navigateToSearchRepositoryScreen(query: String) {
        navigate("$route/${query.encode()}")
    }

    fun NavBackStackEntry.getSearchRepositoryScreenQueryArg(): String {
        return checkNotNull(arguments?.getString(queryArg)).decode()
    }

    fun SavedStateHandle.getSearchRepositoryScreenQueryArg(): String {
        return checkNotNull(this.get<String>(queryArg)).decode()
    }
}
