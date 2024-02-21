package org.ageage.githubclient.feature.searchrepository

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import org.ageage.githubclient.core.model.GitHubRepository
import org.ageage.githubclient.core.model.Owner
import org.ageage.githubclient.core.ui.component.ApiErrorDialog
import org.ageage.githubclient.core.ui.screenconfig.SearchRepositoryScreenConfig
import org.ageage.githubclient.core.ui.screenconfig.SearchRepositoryScreenConfig.getSearchRepositoryScreenQueryArg
import org.ageage.githubclient.core.ui.theme.GitHubClientTheme
import org.ageage.githubclient.core.ui.util.ApiErrorState
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
    val apiErrorState by viewModel.apiErrorStateHelper.errorState.collectAsStateWithLifecycle()

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
        apiErrorState = apiErrorState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchRepositoryScreenContent(
    uiState: SearchRepositoryScreenState,
    apiErrorState: ApiErrorState,
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
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = uiState.gitHubRepositories,
                key = { it.id }
            ) {
                GitHubRepositoryDetailCard(
                    gitHubRepository = it,
                    onClick = {
                        onEvent(
                            SearchRepositoryScreenEvent.OnGitHubRepositoryDetailCardClick(
                                it.name,
                                it.owner.name
                            )
                        )
                    }
                )
            }
        }
    }

    ApiErrorDialog(
        apiErrorState = apiErrorState,
        onDismissRequest = {
            onEvent(SearchRepositoryScreenEvent.OnApiErrorDialogDismissRequest)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GitHubRepositoryDetailCard(
    gitHubRepository: GitHubRepository,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(CardDefaults.shape)
            .clickable { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    model = gitHubRepository.owner.avatarUrl,
                    contentDescription = gitHubRepository.owner.name
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    modifier = Modifier.weight(weight = 1f, fill = false),
                    text = gitHubRepository.fullName,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.size(8.dp))
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.baseline_star_24),
                    contentDescription = null
                )
                Text(text = gitHubRepository.stargazersCount.toString())
            }
            Spacer(modifier = Modifier.size(8.dp))
            gitHubRepository.description?.let { description ->
                Text(
                    text = description,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                gitHubRepository.topics.forEach { topic ->
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
        }
    }
}

@Preview
@Composable
private fun Preview_SearchRepositoryContent() {
    GitHubClientTheme {
        SearchRepositoryScreenContent(
            uiState = SearchRepositoryScreenState(
                searchQuery = "検索キーワード",
                gitHubRepositories = (1..10).map { index ->
                    GitHubRepository(
                        id = index.toLong(),
                        name = "repo$index",
                        fullName = "user$index/repo$index",
                        description = "description_description_description_description_description",
                        stargazersCount = 100 * index,
                        topics = listOf("topic${index * 1}", "topic${index * 2}"),
                        owner = Owner(
                            name = "user$index",
                            avatarUrl = "https://example.com/image.png"
                        )
                    )
                }
            ),
            apiErrorState = ApiErrorState.None,
            onEvent = {}
        )
    }
}
