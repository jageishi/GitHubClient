package org.ageage.githubclient.feature.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ageage.githubclient.core.ui.screenconfig.HomeScreenConfig
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
fun HomeScreen(
    navController: NavController = rememberNavController()
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CollectWithLifecycle(viewModel.effect) {
        when (it) {
            HomeScreenEffect.NavigateToSearchRepositories -> {
                Log.d("HomeScreen", "OnButtonClick")
            }
        }
    }

    HomeContent(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    uiState: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home_top_app_bar_title))
                },
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
                        onEvent(HomeScreenEvent.OnKeyboardActionSearch)
                    }
                ),
            )
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp, end = 16.dp),
                onClick = { onEvent(HomeScreenEvent.OnSearchButtonClick) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(id = R.string.home_button_search))
            }
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    GitHubClientTheme {
        HomeContent(
            uiState = HomeScreenState(),
            onEvent = {}
        )
    }
}
