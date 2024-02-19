package org.ageage.githubclient.feature.searchrepository

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.ageage.githubclient.core.ui.screenconfig.SearchRepositoryScreenConfig
import org.ageage.githubclient.core.ui.screenconfig.SearchRepositoryScreenConfig.getSearchRepositoryScreenQueryArg
import org.ageage.githubclient.core.ui.theme.GitHubClientTheme
import org.ageage.githubclient.core.ui.util.CollectWithLifecycle

fun NavGraphBuilder.searchRepositoryScreen(
    navController: NavController
) {
    composable(
        route = SearchRepositoryScreenConfig.routeWithArgs,
        arguments = SearchRepositoryScreenConfig.arguments
    ) { navBackStackEntry ->
        SearchRepositoryScreen(
            navController = navController,
            searchQuery = navBackStackEntry.getSearchRepositoryScreenQueryArg()
        )
    }
}

@Composable
private fun SearchRepositoryScreen(
    navController: NavController,
    searchQuery: String
) {
    val viewModel: SearchRepositoryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CollectWithLifecycle(viewModel.effect) {
        when (it) {
            SearchRepositoryScreenEffect.NavigateUp -> {
                navController.navigateUp()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(SearchRepositoryScreenEvent.OnCreate(searchQuery))
    }

    SearchRepositoryScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchRepositoryScreenContent(
    uiState: SearchRepositoryScreenState,
    onEvent: (SearchRepositoryScreenEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(SearchRepositoryScreenEvent.OnTopAppBarBackArrowClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = uiState.searchQuery,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun Preview_SearchRepositoryContent() {
    GitHubClientTheme {
        SearchRepositoryScreenContent(
            uiState = SearchRepositoryScreenState(
                searchQuery = "検索キーワード"
            ),
            onEvent = {}
        )
    }
}
