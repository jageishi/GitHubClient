package org.ageage.githubclient.core.ui.screenconfig

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.ageage.githubclient.common.extension.decode
import org.ageage.githubclient.common.extension.encode

data object RepositoryDetailScreenConfig : ScreenConfig {
    override val route: String = "repository_detail"
    private const val ownerArg = "owner"
    private const val repoArg = "repo"
    val routeWithArgs = "$route/{$ownerArg}/{$repoArg}"
    val arguments = listOf(
        navArgument(ownerArg) {
            type = NavType.StringType
        },
        navArgument(repoArg) {
            type = NavType.StringType
        }
    )

    fun NavController.navigateToRepositoryDetailScreen(owner: String, repo: String) {
        navigate("$route/${owner.encode()}/${repo.encode()}")
    }

    fun SavedStateHandle.getRepositoryDetailScreenOwnerArg(): String {
        return checkNotNull(this.get<String>(ownerArg)).decode()
    }

    fun SavedStateHandle.getRepositoryDetailScreenRepoArg(): String {
        return checkNotNull(this.get<String>(repoArg)).decode()
    }
}