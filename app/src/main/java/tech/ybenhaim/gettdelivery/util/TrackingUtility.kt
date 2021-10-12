package tech.ybenhaim.gettdelivery.util

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions
import tech.ybenhaim.gettdelivery.data.models.MyLocation

fun Location.toMyLocation(): tech.ybenhaim.gettdelivery.data.models.Location {
    return tech.ybenhaim.gettdelivery.data.models.Location(
        latitude = this.latitude,
        longitude = this.longitude,
        time = 1L
    )
}

object TrackingUtility {

    fun hasLocationPermissions(context: Context) =
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
}
