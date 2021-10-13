package tech.ybenhaim.gettdelivery.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.roads.RoadSnap
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.roads.SnappedPoint

interface RoadsApi {

    @GET("snapToRoads")
    suspend fun getSnappedPoints(
        @Query("path") path: String,
        @Query("interpolate") interpolate: String,
        @Query("key") key: String

    ): Response<RoadSnap>
}