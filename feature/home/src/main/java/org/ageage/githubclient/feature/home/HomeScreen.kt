package org.ageage.githubclient.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.ageage.githubclient.core.ui.screenconfig.HomeScreenConfig
import org.ageage.githubclient.core.ui.screenconfig.SearchRepositoryScreenConfig.navigateToSearchRepositoryScreen
import org.ageage.githubclient.core.ui.theme.GitHubClientTheme
import org.ageage.githubclient.core.ui.util.CollectWithLifecycle

fun NavGraphBuilder.homeScreen(
    navController: NavController
) {
    composable(HomeScreenConfig.route) {
        HomeScreen(navController)
    }
}

@Composable
private fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    CollectWithLifecycle(viewModel.effect) {
        when (it) {
            is HomeScreenEffect.NavigateToSearchRepositoryScreen -> {
                focusManager.clearFocus()
                navController.navigateToSearchRepositoryScreen(it.query)
            }
        }
    }

    HomeScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home_top_app_bar_title))
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = uiState.searchQuery,
                onValueChange = {
                    onEvent(HomeScreenEvent.OnSearchQueryChange(it))
                },
                placeholder = { Text(text = stringResource(id = R.string.home_text_field_placeholder)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onEvent(HomeScreenEvent.OnKeyboardActionSearch(uiState.searchQuery))
                    }
                ),
                isError = uiState.shouldShowEmptyQueryErrorText
            )
            Row {
                if (uiState.shouldShowEmptyQueryErrorText) {
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 8.dp,
                                end = 8.dp
                            ),
                        text = stringResource(id = R.string.home_empty_query_notification_message),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier.padding(top = 8.dp, end = 16.dp),
                    onClick = { onEvent(HomeScreenEvent.OnSearchButtonClick(uiState.searchQuery)) },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = stringResource(id = R.string.home_button_search))
                }
            }
        }

    }
}

@Preview
@Composable
private fun Preview_HomeScreenContent_Default() {
    GitHubClientTheme {
        HomeScreenContent(
            uiState = HomeScreenState(),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun Preview_HomeScreenContent_Error() {
    GitHubClientTheme {
        HomeScreenContent(
            uiState = HomeScreenState(
                shouldShowEmptyQueryErrorText = true
            ),
            onEvent = {}
        )
    }
}
