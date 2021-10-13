package tech.ybenhaim.gettdelivery.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.GoogleDirections

interface DirectionsApi {

/*
    Interface for receiving directions to next pickup/drop-off point from the Google Directions Api.
*/

    @GET("json")
    suspend fun getDirections(
                      @Query("origin") origin: String,
                      @Query("destination") destination: String,
                      @Query("key") key: String

    ): Response<GoogleDirections>

}