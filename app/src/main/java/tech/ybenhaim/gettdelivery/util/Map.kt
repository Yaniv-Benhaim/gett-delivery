package tech.ybenhaim.gettdelivery.util

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.core.content.edit
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import tech.ybenhaim.gettdelivery.R

import timber.log.Timber

fun GoogleMap.setCustomStyle(context: Context) {
    try {
        val success = this.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                context,
                R.raw.style
            )
        )
        if(!success) {
            Timber.d("Failed to set map style")
        }
    } catch (e: Exception) {
        Timber.d("STYLING: $e")
    }
}

fun Location?.toText(): String {
    return if (this != null) {
        "($latitude, $longitude)"
    } else {
        "Unknown location"
    }
}

internal object SharedPreferenceUtil {

    const val KEY_FOREGROUND_ENABLED = "tracking_foreground_location"

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The [Context].
     */
    fun getLocationTrackingPref(context: Context): Boolean =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            .getBoolean(KEY_FOREGROUND_ENABLED, false)

    /**
     * Stores the location updates state in SharedPreferences.
     * @param requestingLocationUpdates The location updates state.
     */
    fun saveLocationTrackingPref(context: Context, requestingLocationUpdates: Boolean) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE).edit {
            putBoolean(KEY_FOREGROUND_ENABLED, requestingLocationUpdates)
        }
}


