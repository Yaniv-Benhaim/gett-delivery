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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
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
    val currentMarker = mutableMapOf<Int, Marker>()
    var didRequestDirections = false
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
                    setupMap(map, context, viewModel)
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
            viewModel.getCurrentLocation().collect {
                Timber.tag("LOCUPDATES").d( "in collect homescreen ${it.last().latitude}")
                //Setting last current location in viewModel

                viewModel.currentLocation.value = it.last()
                //Getting directions from last current location to destination
                if (!didRequestDirections) {
                    viewModel.getDirections()
                    didRequestDirections = true
                }
                //Adding directions as polyline to map with extension function
                gMap?.addPolylinesToMap(directions)
                //Adding or moving marker of current location
                if (it.isNotEmpty()) {
                    val location = LatLng(it.last().latitude, it.last().longitude)
                    gMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            location,
                            MAP_ZOOM
                        )
                    )
                    gMap?.let { map ->
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
                }
            }
//        } catch (e: Exception) {
//            Timber.tag("LOCUPDATES").e("Failed to collect in homescreen line 122 $e")
//        }
    }
}


private fun setupMap(map: GoogleMap, context: Context, viewModel: MainViewModel) {
    map.isBuildingsEnabled = true
    map.setCustomStyle(context)
    map.uiSettings.apply {
        isZoomGesturesEnabled = true
        isZoomControlsEnabled = false
        isCompassEnabled = false
        isMapToolbarEnabled = false
        isMyLocationButtonEnabled = true
    }

    Log.d("LOCUPDATES", "setupMap Called")
}

