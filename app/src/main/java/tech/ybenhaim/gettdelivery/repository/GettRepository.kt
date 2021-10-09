package tech.ybenhaim.gettdelivery.repository

import dagger.hilt.android.scopes.ActivityScoped
import tech.ybenhaim.gettdelivery.data.remote.api.DeliveryApi
import tech.ybenhaim.gettdelivery.data.remote.responses.NavigationRoute
import tech.ybenhaim.gettdelivery.util.Resource
import javax.inject.Inject

@ActivityScoped
class GettRepository @Inject constructor(
    private val api: DeliveryApi
) {

    suspend fun getDeliveryRoute() : Resource<NavigationRoute> {
        val response = try {
            api.getDeliveryRoute()
        } catch (e: Exception) {
            return Resource.Error("Error getting response: $e")
        }
        return Resource.Success(response)
    }
}