package org.ageage.githubclient.feature.home

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.ageage.githubclient.core.ui.util.CollectWithLifecycle

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()

    CollectWithLifecycle(viewModel.effect) {
        when (it) {
            HomeViewModel.Effect.OnButtonClick -> {
                Log.d("HomeScreen", "OnButtonClick")
            }
        }
    }

    Scaffold { padding ->
        Button(
            modifier = Modifier.padding(padding),
            onClick = viewModel::onButtonClick
        ) {
            Text(text = "Button")
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
