package tech.ybenhaim.gettdelivery.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.ybenhaim.gettdelivery.data.Constants.HISTORY_SCREEN
import tech.ybenhaim.gettdelivery.data.Constants.HOME_SCREEN
import tech.ybenhaim.gettdelivery.data.Constants.PERMISSIONS_SCREEN
import tech.ybenhaim.gettdelivery.data.Constants.PICKUP_SCREEN
import tech.ybenhaim.gettdelivery.data.Constants.PROFILE_SCREEN
import tech.ybenhaim.gettdelivery.data.Constants.SETTINGS_SCREEN
import tech.ybenhaim.gettdelivery.data.Constants.SPLASH_SCREEN
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
    NavHost(navController = navController, startDestination = SPLASH_SCREEN ) {
        composable(HOME_SCREEN) {
            HomeScreen(viewModel, navController)
        }
        composable(HISTORY_SCREEN) {
            SearchScreen()
        }
        composable(PROFILE_SCREEN) {
            ProfileScreen()
        }
        composable(SETTINGS_SCREEN) {
            SettingsScreen()
        }
        composable(SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }
        composable(PERMISSIONS_SCREEN) {
            PermissionsScreen(navController = navController)
        }
        composable(PICKUP_SCREEN) {
            PickupScreen(viewModel = viewModel, navController = navController)
        }
    }
}



