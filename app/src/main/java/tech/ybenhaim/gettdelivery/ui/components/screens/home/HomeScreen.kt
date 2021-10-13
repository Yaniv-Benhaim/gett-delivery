package tech.ybenhaim.gettdelivery.ui.components.screens.home

import android.content.Context
import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import tech.ybenhaim.gettdelivery.R
import tech.ybenhaim.gettdelivery.data.Constants.ARRIVED_BUTTON_TEXT
import tech.ybenhaim.gettdelivery.ui.components.elements.progressbars.CircularProgressBar
import tech.ybenhaim.gettdelivery.ui.components.elements.buttons.GradientButton
import tech.ybenhaim.gettdelivery.ui.components.elements.info.InfoScreen
import tech.ybenhaim.gettdelivery.ui.theme.Purple500
import tech.ybenhaim.gettdelivery.ui.theme.Purple700
import tech.ybenhaim.gettdelivery.data.Constants.MAP_ZOOM
import tech.ybenhaim.gettdelivery.data.Constants.PICKUP_SCREEN
import tech.ybenhaim.gettdelivery.data.Constants.TAG_FLOW_COLLECTION
import tech.ybenhaim.gettdelivery.ui.components.elements.dialog.NeedsPermissionsDialog
import tech.ybenhaim.gettdelivery.util.googlemaps.addPolylinesToMap
import tech.ybenhaim.gettdelivery.util.googlemaps.LatLngInterpolator
import tech.ybenhaim.gettdelivery.util.googlemaps.MarkerAnimation
import tech.ybenhaim.gettdelivery.util.googlemaps.setCustomStyle
import tech.ybenhaim.gettdelivery.util.tracking.TrackingUtility
import tech.ybenhaim.gettdelivery.viewmodels.MainViewModel
import timber.log.Timber




@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavController) {

    val context = LocalContext.current
    val navigationRoute by remember { viewModel.navigationRoutes }
    val currentTask by remember { viewModel.currentTask }
    val isLoading by remember { viewModel.isLoading }
    var showPermissionDialog by remember { viewModel.needsPermission }
    val currentAzimuth by remember { viewModel.currentAzimuth }
    var finishedLoadingMap by remember { mutableStateOf(false) }

    //This if statement refreshes the map if the user accepts location permissions
    if(!showPermissionDialog) {

        //Box that contains the Google Map
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 300.dp)
                ,
            contentAlignment = Alignment.TopCenter
        ) {

            if(isLoading) {
                CircularProgressBar(isLoading)
            } else {
                DeliveryMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    onReady = { map ->
                        setupMap(map, context, viewModel, showPermissionDialog)
                        finishedLoadingMap = true
                    },
                    reload = showPermissionDialog
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .shadow(5.dp),
            verticalAlignment = Alignment.Top,


            ) {
            InfoScreen(pickupPoint = currentTask.geo.address, currentTask.type)
        }



        Row(
            modifier = Modifier
                .fillMaxSize()
                .shadow(5.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            HomeBottomView(
                onClick = {
                    viewModel.getNextTask()
                    navController.navigate(PICKUP_SCREEN)

                })
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 110.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {



            Row(
                Modifier.padding(end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                CompassArrow(viewModel = viewModel)
            }
        }





            

        //If the user did not give permissions this dialog pops up
        NeedsPermissionsDialog(showDialog = showPermissionDialog, navController = navController)
    }
}



@ExperimentalCoroutinesApi
private fun setupMap(map: GoogleMap, context: Context, viewModel: MainViewModel, needsPermission: Boolean) {

    /*
        From here we collect multiple streams of data:
        1 Current location
        2 Current task
        3 Current directions to pickup/drop-of points

        Setup Flow:
        1 Setup basic map features
        2 Collect current list of user's location
        3 Collect directions from user's location to destination
        4 Add directions to map as a poly line from user's location to destination
        5 Add user's location to map as a marker
        6 Animate user's location to next location received in flow

        Note!
        The setup of this map should be separated in to smaller extensions & functions.
     */

//BASIC MAP SETUP ***********************************************************
    var didRequestDirections = false
    val currentMarker = mutableMapOf<Int, Marker>()
    map.isBuildingsEnabled = true
    map.setCustomStyle(context)
    map.uiSettings.apply {
        isZoomGesturesEnabled = true
        isZoomControlsEnabled = false
        isCompassEnabled = false
        isMapToolbarEnabled = false
        isMyLocationButtonEnabled = true
//END BASIC MAP SETUP *******************************************************

//START COROUTINE TO COLLECT FLOW STREAMS ***********************************

        CoroutineScope(Dispatchers.Main).launch {

            //Getting location and updating camera and marker
            if (TrackingUtility.hasLocationPermissions(context) && !needsPermission) {

//START COLLECTING CURRENT LOCATION ****************************************

                viewModel.getCurrentLocation().collect {

                    //Setting last current location in viewModel
                    viewModel.currentLocation.value = it.last()

//START COLLECTING CURRENT DIRECTIONS **************************************

                    if (!didRequestDirections) {
                        viewModel.getDirections().collect { directions ->
                            directions?.let {
                                viewModel.directions.value = directions
                            }
                        }
                        didRequestDirections = true
                    }
//END COLLECTING CURRENT DIRECTIONS ****************************************

//ADDING DIRECTIONS TO MAP *************************************************

                    map.addPolylinesToMap(viewModel.directions.value)

//ADDING OR ANIMATING MARKER TO CURRENT LOCATION ***************************

                    if (it.isNotEmpty()) {
                        val location = LatLng(it.last().latitude, it.last().longitude)
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                location,
                                MAP_ZOOM
                            )
                        )

                        map.let { map ->
                            if (currentMarker[1] == null) {
                                currentMarker[1] = map.addMarker(
                                    MarkerOptions()
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
                                        .position(location)
                                )!!
                            } else MarkerAnimation.animateMarkerToGB(
                                currentMarker[1]!!,
                                location,
                                LatLngInterpolator.Spherical()
                            )
                        }

                    } else {
                        Timber.tag(TAG_FLOW_COLLECTION).d("Homescreen list collect is empty")
                    }
                }
//FINISHED ADDING OR ANIMATING MARKER TO CURRENT LOCATION *********************

            } else {
                viewModel.needsPermission.value = true
            }
        }
    }
}

