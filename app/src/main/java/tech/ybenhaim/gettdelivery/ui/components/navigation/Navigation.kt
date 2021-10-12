package tech.ybenhaim.gettdelivery.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.ybenhaim.gettdelivery.ui.components.screens.home.HomeScreen
import tech.ybenhaim.gettdelivery.ui.components.screens.permissions.PermissionsScreen
import tech.ybenhaim.gettdelivery.ui.components.screens.pickup.PickupScreen
import tech.ybenhaim.gettdelivery.ui.components.screens.profile.ProfileScreen
import tech.ybenhaim.gettdelivery.ui.components.screens.history.SearchScreen
import tech.ybenhaim.gettdelivery.ui.components.screens.settings.SettingsScreen
import tech.ybenhaim.gettdelivery.ui.components.screens.splash.SplashScreen
import tech.ybenhaim.gettdelivery.viewmodels.MainViewModel

@ExperimentalCoroutinesApi
@Composable
fun Navigation(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "splash" ) {
        composable("home") {
            HomeScreen(viewModel, navController)
        }
        composable("history") {
            SearchScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
        composable("settings") {
            SettingsScreen()
        }
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("permissions") {
            PermissionsScreen(navController = navController)
        }
        composable("pickup") {
            PickupScreen(viewModel = viewModel, navController = navController)
        }
    }
}



