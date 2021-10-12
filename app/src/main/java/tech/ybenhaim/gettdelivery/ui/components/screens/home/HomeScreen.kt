package tech.ybenhaim.gettdelivery.ui.components.screens.home

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.GoogleDirections
import tech.ybenhaim.gettdelivery.ui.components.CircularProgressBar
import tech.ybenhaim.gettdelivery.ui.components.buttons.GradientButton
import tech.ybenhaim.gettdelivery.ui.theme.Purple500
import tech.ybenhaim.gettdelivery.ui.theme.Purple700
import tech.ybenhaim.gettdelivery.util.Constants.MAP_ZOOM
import tech.ybenhaim.gettdelivery.util.addPolylinesToMap
import tech.ybenhaim.gettdelivery.util.googlemaps.LatLngInterpolator
import tech.ybenhaim.gettdelivery.util.googlemaps.MarkerAnimation
import tech.ybenhaim.gettdelivery.util.permissions.getActivity
import tech.ybenhaim.gettdelivery.util.permissions.isFirstRun
import tech.ybenhaim.gettdelivery.util.setCustomStyle
import tech.ybenhaim.gettdelivery.viewmodels.MainViewModel
import timber.log.Timber


var didRequestDirections = false

@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(viewModel: MainViewModel) {

    val context = LocalContext.current
    val navigationRoute by remember { viewModel.navigationRoutes }
    val currentTask by remember { viewModel.currentTask }
    val isLoading by remember { viewModel.isLoading }
    val directions by remember { viewModel.directions }
    val currentAzimuth by remember { viewModel.currentAzimuth }
    var gMap: GoogleMap? = null


    var finishedLoadingMap by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(isLoading) {
            CircularProgressBar(isLoading)
        } else {
            DeliveryMap(
                modifier = Modifier
                    .fillMaxSize(),
                onReady = { map ->
                    gMap = map
                    setupMap(map, context, viewModel, directions)
                    finishedLoadingMap = true
                }
            )
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(bottom = 60.dp)
            .fillMaxSize()
    ) {
        GradientButton(
            text = "Arrived",
            textColor = Color.White,
            gradient =  Brush.horizontalGradient(listOf(Purple700, Purple500)),
            onClick = { viewModel.getNextTask() }
        )
    }


    LaunchedEffect(key1 = finishedLoadingMap) {

        //try {
            //Collecting Flow of current location

//        } catch (e: Exception) {
//            Timber.tag("LOCUPDATES").e("Failed to collect in homescreen line 122 $e")
//        }
    }
}


@ExperimentalCoroutinesApi
private fun setupMap(map: GoogleMap, context: Context, viewModel: MainViewModel, directions: GoogleDirections) {
    val currentMarker = mutableMapOf<Int, Marker>()
    map.isBuildingsEnabled = true
    map.setCustomStyle(context)
    map.uiSettings.apply {
        isZoomGesturesEnabled = true
        isZoomControlsEnabled = false
        isCompassEnabled = false
        isMapToolbarEnabled = false
        isMyLocationButtonEnabled = true

        CoroutineScope(Dispatchers.Main).launch {
            //Getting location and updating camera and marker
            viewModel.getCurrentLocation().collect {

                //Setting last current location in viewModel
                viewModel.currentLocation.value = it.last()

                //Getting directions from last current location to destination
                if (!didRequestDirections) {
                    viewModel.getDirections().collect { directions ->
                        Timber.tag("LOCUPDATES").d( "Collected directions status ${directions?.status}")
                        directions?.let {
                            viewModel.directions.value = directions
                        }
                    }
                    didRequestDirections = true
                }
                //Adding directions as polyline to map with extension function
                map.addPolylinesToMap(viewModel.directions.value)

                //Adding or moving marker of current location
                if (it.isNotEmpty()) {
                    val location = LatLng(it.last().latitude, it.last().longitude)
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            location,
                            MAP_ZOOM
                        )
                    )
                    Timber.tag("LOCUPDATES").d("Homescreen after camera update ")

                    map.let { map ->
                        if (currentMarker[1] == null) {
                            currentMarker[1] = map.addMarker(
                                MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromResource(tech.ybenhaim.gettdelivery.R.drawable.ic_location))
                                    .position(location)
                            )!!
                        } else MarkerAnimation.animateMarkerToGB(
                            currentMarker[1]!!,
                            location,
                            LatLngInterpolator.Spherical()
                        )
                    }

                } else {
                    Timber.tag("LOCUPDATES").d("Homescreen list collect is empty")
                }
            }
        }
    }
}

