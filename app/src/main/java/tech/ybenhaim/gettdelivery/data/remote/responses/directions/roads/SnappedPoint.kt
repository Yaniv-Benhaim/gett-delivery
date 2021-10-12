package tech.ybenhaim.gettdelivery.data.remote.responses.directions.roads

data class SnappedPoint(
    val location: Location,
    val originalIndex: Int,
    val placeId: String
)