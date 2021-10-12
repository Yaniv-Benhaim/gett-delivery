package tech.ybenhaim.gettdelivery.viewmodels

import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tech.ybenhaim.gettdelivery.data.models.MyLocation
import tech.ybenhaim.gettdelivery.data.remote.responses.deliveries.Geo
import tech.ybenhaim.gettdelivery.data.remote.responses.deliveries.NavigationRoute
import tech.ybenhaim.gettdelivery.data.remote.responses.deliveries.NavigationRouteItem
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.Directions
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.GoogleDirections
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.Route
import tech.ybenhaim.gettdelivery.repository.GettRepository
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GettRepository
    ) : ViewModel() {

    var navigationRoutes = mutableStateOf(NavigationRoute())
    var currentTask = mutableStateOf(NavigationRouteItem(Geo("", 0.0, 0.0), emptyList(), "empty", ""))
    var isLoading = mutableStateOf(true)
    var currentLocation = mutableStateOf(MyLocation(latitude = 32.07882010000863, longitude = 34.790416514658150, bearing = 0f, speed = 0f))
    var currentLocationL = MutableLiveData<MyLocation>()
    val directions = mutableStateOf(GoogleDirections(emptyList(), emptyList(), "empty"))
    val currentAzimuth = mutableStateOf(0.0f)

    init {
       loadDeliveryRoute()
    }

    fun getCurrentLocation() = repository.getCurrentLocation()

    @ExperimentalCoroutinesApi
    fun loadDeliveryRoute() = viewModelScope.launch {
        isLoading.value = true
        repository.getDeliveryRoute().collect { navRoute ->
            navRoute.data?.let {
                navigationRoutes.value = it
                if(currentTask.value.state == "empty") { currentTask.value = it.first() }
                delay(1000)
                isLoading.value = false
            }

        }
    }

    fun getNextTask() {
        viewModelScope.launch {
            navigationRoutes.value.forEachIndexed { index, navigationRouteItem ->
                if (currentTask.value == navigationRouteItem) {
                    currentTask.value = navigationRoutes.value[index.plus(1)]
                }
            }
        }
    }


    fun getDirections() = viewModelScope.launch {

        repository.getDirections(
            "${currentLocation.value.latitude},${currentLocation.value.longitude}",
            currentTask.value.geo.address
        ).collect { routes ->
            routes?.let {
                directions.value = it
                Log.d("MAPURL", "collecting directions")
            }
        }
    }

    fun updateCurrentLocation(newLocation: Location) {
        if (newLocation.latitude != 0.0) {
            val updatedLocation = MyLocation(
                latitude = newLocation.latitude,
                longitude = newLocation.longitude,
                bearing = newLocation.bearing,
                speed = newLocation.speed
            )
            currentLocation.value = updatedLocation
            currentLocationL.value = updatedLocation
        } else {
            val updatedLocation = MyLocation(
                latitude = 32.07882010000863,
                longitude = 34.790416514658150,
                bearing = newLocation.bearing,
                speed = newLocation.speed
            )
            currentLocation.value = updatedLocation
        }
    }

    fun updateCurrentRotation(azimuth: Float) {
        currentAzimuth.value = azimuth
    }

    fun currentRotation() = channelFlow {
        send(currentAzimuth.value)
    }

}