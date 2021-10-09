package tech.ybenhaim.gettdelivery.data.remote.responses

data class NavigationRouteItem(
    val geo: Geo,
    val parcels: List<Parcel>,
    val state: String,
    val type: String
)