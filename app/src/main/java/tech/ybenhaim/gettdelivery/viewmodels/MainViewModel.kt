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
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GettRepository
    ) : ViewModel() {

    var navigationRoutes = mutableStateOf(NavigationRoute())
    var amountOfTasks = mutableStateOf(0)
    var curTaskNumber = mutableStateOf(0)
    var currentTask =
        mutableStateOf(NavigationRouteItem(Geo("", 0.0, 0.0), emptyList(), "empty", ""))
    var lastDeliveryTask =
        mutableStateOf(NavigationRouteItem(Geo("", 0.0, 0.0), emptyList(), "empty", ""))
    var isLastTask = mutableStateOf(false)
    var isLoading = mutableStateOf(true)
    var currentLocation = mutableStateOf(
        MyLocation(
            latitude = 32.07882010000863,
            longitude = 34.790416514658150,
            bearing = 0f,
            speed = 0f
        )
    )
    val directions = mutableStateOf(GoogleDirections(emptyList(), emptyList(), "empty"))
    val currentAzimuth = mutableStateOf(0.0f)
    var needsPermission = mutableStateOf(false)

    init {
        loadDeliveryRoute()
    }

    //fun getCurrentLocation() = repository.getCurrentLocation()
    suspend fun getCurrentLocation() = getCurrentSnappedLocation()

    private suspend fun getCurrentSnappedLocation(): Flow<List<MyLocation>> = flow {

        var rawPath = ""
        var counter = 0
        val snappedLocationPoints = ArrayList<MyLocation>()
        val originalPoints = ArrayList<MyLocation>()
        val failedToGetSnappedPoints = mutableStateOf(false)
        val firstRequest = mutableStateOf(true)


        repository.getCurrentLocation().collect {
            if(counter < 1 && firstRequest.value) {
                emit(it)
                firstRequest.value = false
            }
            Timber.tag("LOCUPDATES").e("original points: ${it.last().latitude} + counter = $counter")
            if (counter < 3) {
                rawPath += "${it.last().latitude},${it.last().longitude}|"
                originalPoints.add(it.last())
                counter++
            } else {
                try {
                    val snappedPoints = repository.getSnappedPoints(rawPath.substring(0, rawPath.length - 2))

                    if (snappedPoints.isSuccessful) {
                        Timber.tag("LOCUPDATES").e("Roadsnap body: ${snappedPoints.body()}")

                        snappedPoints.body()?.snappedPoints?.forEach { snapped ->

                            snappedLocationPoints.add(
                                MyLocation(
                                    latitude = snapped.location.latitude,
                                    longitude = snapped.location.longitude,
                                    bearing = 0.0f,
                                    speed = 0.0f
                                )
                            )
                        }
                        emit(snappedLocationPoints)
                        originalPoints.clear()
                        snappedLocationPoints.clear()
                        counter = 0
                        failedToGetSnappedPoints.value = false
                    } else {
                        Timber.tag("LOCUPDATES").e("snapped points unsuccesfull: ${snappedPoints.errorBody()}")
                        emit(originalPoints)
                        originalPoints.clear()
                        snappedLocationPoints.clear()
                        counter = 0
                        failedToGetSnappedPoints.value = true
                    }


                } catch (e: Exception) {
                    Timber.tag("LOCUPDATES").e("snapped points: $e")
                    emit(originalPoints)
                    originalPoints.clear()
                    snappedLocationPoints.clear()
                    counter = 0
                    failedToGetSnappedPoints.value = true
                }
            }
        }
    }


    @ExperimentalCoroutinesApi
    fun loadDeliveryRoute() = viewModelScope.launch {
        isLoading.value = true
        repository.getDeliveryRoute().collect { navRoute ->
            amountOfTasks.value = navRoute.data?.size ?: 0
            lastDeliveryTask.value = navRoute.data?.last() !!
            navRoute.data.let {
                navigationRoutes.value = it

                if(currentTask.value.state == "empty") { currentTask.value = it.first() }
                delay(1000)
                isLoading.value = false
            }

        }
    }

    fun getNextTask() {
        viewModelScope.launch {
            if(curTaskNumber.value < navigationRoutes.value.size - 1) {
                curTaskNumber.value = curTaskNumber.value.plus(1)
                currentTask.value = navigationRoutes.value[curTaskNumber.value]
            } else {
                isLastTask.value = true
            }
            getDirections()
        }
    }

    suspend fun getDirections() =
        repository.getDirections(
            "${currentLocation.value.latitude},${currentLocation.value.longitude}",
                    currentTask.value.geo.address)
}

