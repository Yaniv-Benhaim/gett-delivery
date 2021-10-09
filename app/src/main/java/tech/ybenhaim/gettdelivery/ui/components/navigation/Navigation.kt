package tech.ybenhaim.gettdelivery.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.ybenhaim.gettdelivery.ui.components.screens.home.HomeScreen
import tech.ybenhaim.gettdelivery.ui.components.screens.profile.ProfileScreen
import tech.ybenhaim.gettdelivery.ui.components.screens.search.SearchScreen
import tech.ybenhaim.gettdelivery.ui.components.screens.settings.SettingsScreen
import tech.ybenhaim.gettdelivery.viewmodels.MainViewModel

@Composable
fun Navigation(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "home" ) {
        composable("home") {
            HomeScreen(viewModel)
        }
        composable("search") {
            SearchScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
        composable("settings") {
            SettingsScreen()
        }
    }
}

