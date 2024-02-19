package org.ageage.githubclient.core.ui.screenconfig

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
        val encodedQuery = URLEncoder.encode(
            query,
            StandardCharsets.UTF_8.toString()
        )
        navigate("$route/${encodedQuery}")
    }

    fun NavBackStackEntry.getSearchRepositoryScreenQueryArg(): String {
        return URLDecoder.decode(
            checkNotNull(arguments?.getString(queryArg)),
            StandardCharsets.UTF_8.toString()
        )
    }
}
