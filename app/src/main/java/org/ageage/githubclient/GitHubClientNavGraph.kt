package org.ageage.githubclient

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.ageage.githubclient.feature.home.homeScreen

@Composable
fun GitHubClientNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        homeScreen(navController)
    }
}