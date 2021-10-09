package tech.ybenhaim.gettdelivery.data.remote.api

import retrofit2.http.GET
import tech.ybenhaim.gettdelivery.data.remote.responses.NavigationRoute

interface DeliveryApi {

    @GET("journey.json")
    suspend fun getDeliveryRoute() : NavigationRoute
}