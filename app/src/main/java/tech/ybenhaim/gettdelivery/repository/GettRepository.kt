package tech.ybenhaim.gettdelivery.repository

import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import tech.ybenhaim.gettdelivery.data.Constants.API_KEY
import tech.ybenhaim.gettdelivery.data.Constants.TAG_NETWORK_REQUESTS
import tech.ybenhaim.gettdelivery.data.db.HistoryDao
import tech.ybenhaim.gettdelivery.data.db.LocationDao
import tech.ybenhaim.gettdelivery.data.models.History
import tech.ybenhaim.gettdelivery.data.remote.api.DeliveryApi
import tech.ybenhaim.gettdelivery.data.remote.api.DirectionsApi
import tech.ybenhaim.gettdelivery.data.remote.api.RoadsApi
import tech.ybenhaim.gettdelivery.data.remote.responses.deliveries.NavigationRoute
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.GoogleDirections
import tech.ybenhaim.gettdelivery.util.network.Resource
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class GettRepository @Inject constructor(
    private val deliveryApi: DeliveryApi,
    private val directionsApi: DirectionsApi,
    private val roadsApi: RoadsApi,
    private val locationDao: LocationDao,
    private val historyDao: HistoryDao
) {

    //Function to get tasks from journey.json: (Currently journey.json resided on my website)
    @ExperimentalCoroutinesApi
    suspend fun getDeliveryRoute() : Flow<Resource<NavigationRoute>> = channelFlow {
        val response = try {
            deliveryApi.getDeliveryRoute()
        } catch (e: Exception) {
           Timber.tag(TAG_NETWORK_REQUESTS).e(e)
        }
        send(Resource.Success(response as NavigationRoute))
    }

    //Function to get directions from point a to b from the Google Directions Api
    @ExperimentalCoroutinesApi
    suspend fun getDirections(origin: String, destination: String) : Flow<GoogleDirections?> = channelFlow {

        try {
            //Getting directions to destination points from the Google Directions Api
            val result = directionsApi.getDirections(
                origin,
                destination,
                API_KEY)


            if(result.isSuccessful) {
                //Request Successful -> emit flow of GoogleDirections
                send(result.body())
            } else {
                //Request Unsuccessful -> log errorBody()
                Timber.tag(TAG_NETWORK_REQUESTS).e(result.errorBody().toString())
            }

        } catch (e: Exception) {
            //Exception occurred -> log error
            Timber.tag(TAG_NETWORK_REQUESTS).e(e)
        }
    }

    //Function to get a list of snapped points from a list of LatLng points
    suspend fun getSnappedPoints(path: String) = roadsApi.getSnappedPoints(path, "true", API_KEY)

    //Function to get a flow of locations from local database
    fun getCurrentLocation() = locationDao.getCurrentLocation()

    suspend fun insertHistoryItem(history: History) = historyDao.insertHistory(history)

    suspend fun updateHistoryItem(history: History) = historyDao.updateHistory(history)

    fun getCurrentHistory() = historyDao.getCurrentHistory()


}