package tech.ybenhaim.gettdelivery.data.remote.responses.directions

import tech.ybenhaim.gettdelivery.data.remote.responses.directions.Route

data class Directions(
    val routes: List<Route>,
    val status: String
)