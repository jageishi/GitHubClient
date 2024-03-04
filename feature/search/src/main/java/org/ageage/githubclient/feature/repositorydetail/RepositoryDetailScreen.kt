package org.ageage.githubclient.feature.repositorydetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import org.ageage.githubclient.core.model.GitHubRepo
import org.ageage.githubclient.core.model.Owner
import org.ageage.githubclient.core.ui.component.ApiErrorDialog
import org.ageage.githubclient.core.ui.screenconfig.RepositoryDetailScreenConfig
import org.ageage.githubclient.core.ui.theme.GitHubClientTheme
import org.ageage.githubclient.core.ui.util.ApiErrorState
import org.ageage.githubclient.core.ui.util.CollectWithLifecycle
import org.ageage.githubclient.feature.searchrepository.R

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
                GitHubRepoDetail(gitHubRepo = uiState.gitHubRepo)
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GitHubRepoDetail(gitHubRepo: GitHubRepo) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                model = gitHubRepo.owner.avatarUrl,
                contentDescription = gitHubRepo.owner.name
            )
            Text(
                text = gitHubRepo.name,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.baseline_star_24),
                contentDescription = null
            )
            Text(text = gitHubRepo.stargazersCount.toString())
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            gitHubRepo.topics.forEach { topic ->
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                        text = topic,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }
        gitHubRepo.description?.let {
            Text(
                text = it,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun Preview_RepositoryDetailContent_Loaded() {
    GitHubClientTheme {
        RepositoryDetailContent(
            uiState = RepositoryDetailScreenState(
                owner = "owner",
                repo = "repo",
                gitHubRepo = GitHubRepo(
                    id = 1,
                    name = "name",
                    fullName = "fullName",
                    description = "description",
                    stargazersCount = 100,
                    topics = listOf("topic1", "topic2"),
                    owner = Owner(
                        name = "owner",
                        avatarUrl = "https://example.com/avatar.png"
                    ),
                    htmlUrl = "https://example.com"
                ),
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