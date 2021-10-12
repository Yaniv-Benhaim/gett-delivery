package tech.ybenhaim.gettdelivery.data.remote.responses.directions

data class GoogleDirections(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<RouteX>,
    val status: String
)