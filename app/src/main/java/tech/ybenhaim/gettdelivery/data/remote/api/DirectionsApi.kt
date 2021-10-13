package tech.ybenhaim.gettdelivery.data.remote.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.Directions
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.GoogleDirections
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.roads.SnappedPoint

interface DirectionsApi {

    @GET("json")
    suspend fun getDirections(
                      @Query("origin") origin: String,
                      @Query("destination") destination: String,
                      @Query("key") key: String

    ): Response<GoogleDirections>

    @GET("snapToRoads")
    suspend fun getSnappedPoints(
        @Query("path") path: String,
        @Query("interpolate") interpolate: String,
        @Query("key") key: String

    ): Response<List<SnappedPoint>>
}