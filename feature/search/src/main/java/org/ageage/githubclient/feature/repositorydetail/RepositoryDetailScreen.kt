package org.ageage.githubclient.feature.repositorydetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.ageage.githubclient.core.ui.component.ApiErrorDialog
import org.ageage.githubclient.core.ui.screenconfig.RepositoryDetailScreenConfig
import org.ageage.githubclient.core.ui.theme.GitHubClientTheme
import org.ageage.githubclient.core.ui.util.ApiErrorState
import org.ageage.githubclient.core.ui.util.CollectWithLifecycle

fun NavGraphBuilder.repositoryDetailScreen(
    navController: NavController
) {
    composable(
        route = RepositoryDetailScreenConfig.routeWithArgs,
        arguments = RepositoryDetailScreenConfig.arguments
    ) {
        RepositoryDetailScreen(navController)
    }
}

@Composable
private fun RepositoryDetailScreen(navController: NavController) {
    val viewModel: RepositoryDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val apiErrorState by viewModel.apiErrorStateHelper.errorState.collectAsStateWithLifecycle()

    CollectWithLifecycle(viewModel.effect) {
        when (it) {
            is RepositoryDetailScreenEffect.NavigateUp -> {
                navController.navigateUp()
            }
        }
    }

    RepositoryDetailContent(
        uiState = uiState,
        apiErrorState = apiErrorState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryDetailContent(
    uiState: RepositoryDetailScreenState,
    apiErrorState: ApiErrorState,
    onEvent: (RepositoryDetailScreenEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(RepositoryDetailScreenEvent.OnTopAppBarBackArrowClick)
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
                        text = "${uiState.owner}/${uiState.repo}",
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
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.gitHubRepo != null) {
                // TODO コンテンツを表示する
            }
        }
    }

    ApiErrorDialog(
        apiErrorState = apiErrorState,
        onDismissRequest = {
            onEvent(RepositoryDetailScreenEvent.OnApiErrorDialogDismissRequest)
        }
    )
}

@Preview
@Composable
private fun Preview_RepositoryDetailContent_Loaded() {
    GitHubClientTheme {
        RepositoryDetailContent(
            uiState = RepositoryDetailScreenState(
                owner = "owner",
                repo = "repo"
            ),
            apiErrorState = ApiErrorState.None,
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun Preview_RepositoryDetailContent_Loading() {
    GitHubClientTheme {
        RepositoryDetailContent(
            uiState = RepositoryDetailScreenState(
                owner = "owner",
                repo = "repo",
                isLoading = true
            ),
            apiErrorState = ApiErrorState.None,
            onEvent = {}
        )
    }
}