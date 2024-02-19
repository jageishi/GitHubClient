package org.ageage.githubclient.core.ui.screenconfig

sealed interface ScreenConfig {
    val route: String
}

data object HomeScreenConfig : ScreenConfig {
    override val route: String = "home"
}
