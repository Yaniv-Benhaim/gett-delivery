package tech.ybenhaim.gettdelivery.util.googlemaps

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import tech.ybenhaim.gettdelivery.data.Constants.TAG_GOOGLE_MAPS
import tech.ybenhaim.gettdelivery.data.models.Coordinate
import tech.ybenhaim.gettdelivery.data.remote.responses.directions.GoogleDirections
import tech.ybenhaim.gettdelivery.util.googlemaps.decode
import timber.log.Timber

//Extension function for adding custom styling to map
fun GoogleMap.setCustomStyle(context: Context) {
    try {
        val success = this.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                context,
                tech.ybenhaim.gettdelivery.R.raw.style
            )
        )
        if(!success) {
            Timber.tag(TAG_GOOGLE_MAPS).e("Did not succeed to set new map style")
        }
    } catch (e: Exception) {
        Timber.tag(TAG_GOOGLE_MAPS).e(e)
    }
}

//Extension for adding decoded directions as polyline to map
fun GoogleMap.addPolylinesToMap(result: GoogleDirections) {

    Handler(Looper.getMainLooper()).post {
        for (route in result.routes) {
            val decodedPath: List<Coordinate> = decode(route.overview_polyline.points)
            val newDecodedPath: MutableList<LatLng> = ArrayList()
            for (latLng in decodedPath) {
                newDecodedPath.add(
                    LatLng(
                        latLng.latitude,
                        latLng.longitude
                    )
                )
            }
            val polyline: Polyline =
                this.addPolyline(PolylineOptions().addAll(newDecodedPath))
            polyline.color = Color.parseColor("#8510d8")
            polyline.isClickable = true
            polyline.endCap = RoundCap()
            polyline.startCap = RoundCap()
            polyline.width = 20f
        }
    }
}



