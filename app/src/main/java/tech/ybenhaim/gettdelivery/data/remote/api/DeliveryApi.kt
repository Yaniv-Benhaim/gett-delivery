package tech.ybenhaim.gettdelivery.data.remote.api

import retrofit2.http.GET
import tech.ybenhaim.gettdelivery.data.remote.responses.deliveries.NavigationRoute

interface DeliveryApi {

/*
    Interface for receiving available tasks.
    For realistic simulation purposes upload the journey.json file to http://gamedev-il.tech/journey.json
*/
    @GET("journey.json")
    suspend fun getDeliveryRoute() : NavigationRoute
}