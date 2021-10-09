package tech.ybenhaim.gettdelivery.ui.components.screens.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import tech.ybenhaim.gettdelivery.testing.CameraAndViewPort
import tech.ybenhaim.gettdelivery.util.setCustomStyle
import tech.ybenhaim.gettdelivery.viewmodels.MainViewModel
import timber.log.Timber


@Composable
fun HomeScreen(viewModel: MainViewModel) {


    val navigationRoute by remember { viewModel.navigationRoutes }
    val loadError by remember { viewModel.loadError }
    val currentLocation by remember { viewModel.currentLocation }


    val context = LocalContext.current
    Toast.makeText(context, "Location $currentLocation", Toast.LENGTH_SHORT).show()
    //viewModel.loadDeliveryRoute()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DeliveryMap(
            modifier = Modifier
                .fillMaxSize(),
            onReady = { map ->
                setupMap(map, context)

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(currentLocation.latitude, currentLocation.longitude) ,18f))
                val markerOptions = MarkerOptions()
                    .title("Your Location")
                    .position(LatLng(currentLocation.latitude, currentLocation.longitude))
                map.addMarker(markerOptions)
            }
        )

    }
}


private fun setupMap(map: GoogleMap, context: Context) {
    map.moveCamera(CameraUpdateFactory.newCameraPosition(CameraAndViewPort.telAviv))
    map.isBuildingsEnabled = true
    map.setCustomStyle(context)
    map.uiSettings.apply {
        isZoomGesturesEnabled = true
        isZoomControlsEnabled = false
        isCompassEnabled = false
        isMapToolbarEnabled = false
    }
}