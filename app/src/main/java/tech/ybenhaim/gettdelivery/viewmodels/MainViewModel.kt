package tech.ybenhaim.gettdelivery.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tech.ybenhaim.gettdelivery.data.models.History
import tech.ybenhaim.gettdelivery.data.models.MyLocation
import tech.ybenhaim.gettdelivery.data.remote.responses.deliveries.Geo
import tech.ybenhaim.gettdelivery.data.remote.responses.deliveries.NavigationRoute
import tech.ybenhaim.gettdelivery.data.remote.responses.deliveries.NavigationRouteItem
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.GoogleDirections
import tech.ybenhaim.gettdelivery.repository.GettRepository
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GettRepository
    ) : ViewModel() {


    var currentTask = mutableStateOf(NavigationRouteItem(Geo("", 0.0, 0.0), emptyList(), "empty", ""))
    var lastDeliveryTask = mutableStateOf(NavigationRouteItem(Geo("", 0.0, 0.0), emptyList(), "empty", ""))
    var navigationRoutes = mutableStateOf(NavigationRoute())
    private var amountOfTasks = mutableStateOf(0)
    private var curTaskNumber = mutableStateOf(0)
    var currentHistory = mutableStateOf(History(parcels_delivered = 6, average_parcels_per_day = 6, kilometers_travelled = 42))

    var isLastTask = mutableStateOf(false)
    var isLoading = mutableStateOf(true)

    val directions = mutableStateOf(GoogleDirections(emptyList(), emptyList(), "empty"))
    val currentAzimuth = mutableStateOf(0)
    var needsPermission = mutableStateOf(false)

    var currentLocation = mutableStateOf(
        MyLocation(
            latitude = 32.07882010000863,
            longitude = 34.790416514658150,
            bearing = 0f,
            speed = 0f
        )
    )

    init {
        //Initializing view model with getting a list of deliveries available from the api
        loadDeliveryRoute()
    }
    //Function to load delivery route from: https://gamedev-il.tech/journey.json (My website to simulate real network request)
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

    //Function to get next delivery/drop-off task in the list of tasks
    fun getNextTask() {
        viewModelScope.launch {
            if(curTaskNumber.value < navigationRoutes.value.size - 1) {
                curTaskNumber.value = curTaskNumber.value.plus(1)
                currentTask.value = navigationRoutes.value[curTaskNumber.value]
            } else {
                isLastTask.value = true
                //Replace with functions to calculate averages
                //did not do this here to save time
                val history = History(
                    parcels_delivered = navigationRoutes.value[0].parcels.size,
                    average_parcels_per_day = 6,
                    kilometers_travelled = 26
                )
                repository.insertHistoryItem(history)
            }
            getDirections()
        }
    }

    //Function that produces a flow of snapped points from the Google Roads Api
    suspend fun getCurrentLocation(): Flow<List<MyLocation>> = flow {

        var rawPath = ""
        var counter = 0
        val snappedLocationPoints = ArrayList<MyLocation>()
        val originalPoints = ArrayList<MyLocation>()
        val failedToGetSnappedPoints = mutableStateOf(false)
        val firstRequest = mutableStateOf(true)

        //Collecting list of LatLng points from local database
        repository.getCurrentLocation().collect {

            //Checking if this is the first location request,
            // if so then we emit the first point just to have a general location to show the user.
            if(counter < 1 && firstRequest.value) {
                emit(it)
                firstRequest.value = false
            }

            //For every 3 Location points we receive,
            // we add them to a list so that we can send them to the Google Roads Api.
            if (counter < 3) {

                //Building a string of latitude & longitude separated by '|' as the Roads Api expects.
                rawPath += "${it.last().latitude},${it.last().longitude}|"
                originalPoints.add(it.last())
                counter++
            } else {

                //Here we make a request to the Google Roads Api for the last 3 Location points we received
                try {

                    val snappedPoints = repository.getSnappedPoints(rawPath.substring(0, rawPath.length - 2))

                    //If request was successful we emit the snapped points instead of the original points
                    if (snappedPoints.isSuccessful) {
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

                        //If the request was not successful we emit the original points we received.
                        emit(originalPoints)
                        originalPoints.clear()
                        snappedLocationPoints.clear()
                        counter = 0
                        failedToGetSnappedPoints.value = true
                    }


                } catch (e: Exception) {

                    //Just to be sure if an Exception occurs we emit a list of the last available points
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

    //Function to get directions from the Google Directions API and produce them as a Flow of Google Directions
    suspend fun getDirections() =
        repository.getDirections(
            "${currentLocation.value.latitude},${currentLocation.value.longitude}",
            currentTask.value.geo.address)


    fun getHistory() = viewModelScope.launch {
        repository.getCurrentHistory().collect {

        }
    }
}

