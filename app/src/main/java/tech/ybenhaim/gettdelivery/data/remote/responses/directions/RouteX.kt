package tech.ybenhaim.gettdelivery.data.remote.responses.directions

data class RouteX(
    val bounds: Bounds,
    val copyrights: String,
    val legs: List<Leg>,
    val overview_polyline: OverviewPolylineX,
    val summary: String,
    val warnings: List<Any>,
    val waypoint_order: List<Any>
)