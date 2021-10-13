package tech.ybenhaim.gettdelivery


import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import tech.ybenhaim.gettdelivery.data.models.BottomNavItem
import tech.ybenhaim.gettdelivery.repository.GettRepository
import tech.ybenhaim.gettdelivery.service.TrackingService
import tech.ybenhaim.gettdelivery.ui.components.navigation.BottomNavBar
import tech.ybenhaim.gettdelivery.ui.components.navigation.Navigation
import tech.ybenhaim.gettdelivery.ui.theme.GettDeliveryTheme
import tech.ybenhaim.gettdelivery.data.Constants.ACTION_PAUSE_SERVICE
import tech.ybenhaim.gettdelivery.data.Constants.ACTION_START_OR_RESUME_SERVICE
import tech.ybenhaim.gettdelivery.data.Constants.ACTION_STOP_SERVICE
import tech.ybenhaim.gettdelivery.data.Constants.IS_FIRST_RUN
import tech.ybenhaim.gettdelivery.data.Constants.REQUEST_CODE_LOCATION_PERMISSION
import tech.ybenhaim.gettdelivery.util.compass.Compass
import tech.ybenhaim.gettdelivery.util.tracking.TrackingUtility
import tech.ybenhaim.gettdelivery.viewmodels.MainViewModel
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = IS_FIRST_RUN)

@AndroidEntryPoint
class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {


    private lateinit var compass: Compass


    @ExperimentalCoroutinesApi
    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var repository: GettRepository


    @ExperimentalCoroutinesApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        setupCompass(this)
        setContent {
            GettDeliveryTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = Icons.Outlined.Home
                                ),
                                BottomNavItem(
                                    name = "History",
                                    route = "history",
                                    icon = Icons.Outlined.List
                                ),
                                BottomNavItem(
                                    name = "Profile",
                                    route = "profile",
                                    icon = Icons.Outlined.Person
                                ),
                                BottomNavItem(
                                    name = "Settings",
                                    route = "settings",
                                    icon = Icons.Outlined.Settings
                                ),
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(navController = navController, viewModel)
                }
            }
        }

    }

    @ExperimentalCoroutinesApi
    private fun setupCompass(context: Context) {
        compass = Compass(context)
        val cl: Compass.CompassListener = getCompassListener()
        compass.setListener(cl)
    }

    @ExperimentalCoroutinesApi
    private fun getCompassListener(): Compass.CompassListener {
        return object : Compass.CompassListener {
            override fun onNewAzimuth(azimuth: Float) {
                //viewModel.updateCurrentRotation(azimuth)
                Log.d("COMPASS", "azimuth: $azimuth")
            }
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(this, TrackingService::class.java).also {
            it.action = action
            this.startService(it)
        }

    private fun stopTracking() {
        sendCommandToService(ACTION_STOP_SERVICE)
    }


    @ExperimentalCoroutinesApi
    fun requestPermissions() {

        if(TrackingUtility.hasLocationPermissions(this)) {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
            viewModel.needsPermission.value = false
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "This app can't function correctly without location permissions",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app can't function correctly without location permissions",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    @ExperimentalCoroutinesApi
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        lifecycleScope.launch {
            delay(2000)
            viewModel.needsPermission.value = false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onStart() {
        super.onStart()
        if(TrackingUtility.hasLocationPermissions(this)){
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
        compass.start()
    }

    override fun onResume() {
        super.onResume()
        compass.start()

    }

    override fun onPause() {
        sendCommandToService(ACTION_PAUSE_SERVICE)
        compass.stop()
        super.onPause()
    }

    override fun onStop() {
        compass.stop()
        stopTracking()
        super.onStop()
    }




}
