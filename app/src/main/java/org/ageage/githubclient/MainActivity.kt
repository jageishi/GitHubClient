package org.ageage.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.ageage.githubclient.core.ui.theme.GitHubClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubClientTheme {
                GitHubClientNavGraph()
            }
        }
    }
}
