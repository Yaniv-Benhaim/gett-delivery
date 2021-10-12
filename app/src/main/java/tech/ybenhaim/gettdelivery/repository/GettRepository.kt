package tech.ybenhaim.gettdelivery.repository

import android.content.res.Resources
import android.text.Layout
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import tech.ybenhaim.gettdelivery.R
import tech.ybenhaim.gettdelivery.data.db.LocationDao
import tech.ybenhaim.gettdelivery.data.remote.api.DeliveryApi
import tech.ybenhaim.gettdelivery.data.remote.api.DirectionsApi
import tech.ybenhaim.gettdelivery.data.remote.responses.deliveries.NavigationRoute
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.Directions
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.GoogleDirections
import tech.ybenhaim.gettdelivery.util.Constants.API_KEY
import tech.ybenhaim.gettdelivery.util.Constants.BASE_DIRECTIONS_URL
import tech.ybenhaim.gettdelivery.util.Resource
import javax.inject.Inject

@ActivityScoped
class GettRepository @Inject constructor(
    private val deliveryApi: DeliveryApi,
    private val directionsApi: DirectionsApi,
    private val locationDao: LocationDao
) {

    @ExperimentalCoroutinesApi
    suspend fun getDeliveryRoute() : Flow<Resource<NavigationRoute>> = channelFlow {
        val response = try {
            deliveryApi.getDeliveryRoute()

        } catch (e: Exception) {
           Log.e("LOCUPDATES", "Failed to get deliveries")
        }
        try {
            send(Resource.Success(response as NavigationRoute))
        } catch (e:Exception) {
            Log.d("LOCUPDATES", "getDeliveryRoute err line 41 repository: $e")
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun getDirections(origin: String, destination: String) : Flow<GoogleDirections?> = channelFlow {

        Log.d("MAPURL", "${BASE_DIRECTIONS_URL}json?origin=$origin&destination=$destination&key=${API_KEY}")
        try {
            val result = directionsApi.getDirections(
                origin,
                destination,
                API_KEY)

            if(result.isSuccessful) {
                send(result.body())
                //Log.d("MAPURL", "${result.body().toString()}")
            } else {
                Log.d("MAPURL", "NOT SUCCESSFUL")
            }
        } catch (e: Exception) {
            Log.e("MAPURL", "Failed to get directions ${e}")
        }
    }

    fun getCurrentLocation() = locationDao.getCurrentLocation()
}