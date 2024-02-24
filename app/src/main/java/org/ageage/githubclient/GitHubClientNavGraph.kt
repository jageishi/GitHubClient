package org.ageage.githubclient

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.ageage.githubclient.core.ui.screenconfig.HomeScreenConfig
import org.ageage.githubclient.feature.home.homeScreen
import org.ageage.githubclient.feature.repositorydetail.repositoryDetailScreen
import org.ageage.githubclient.feature.searchrepository.searchRepositoryScreen

@Composable
fun GitHubClientNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeScreenConfig.route
    ) {
        homeScreen(navController)
        searchRepositoryScreen(navController)
        repositoryDetailScreen(navController)
    }
}