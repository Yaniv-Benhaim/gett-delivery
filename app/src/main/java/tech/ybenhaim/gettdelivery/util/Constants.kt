package tech.ybenhaim.gettdelivery.util

import androidx.compose.ui.graphics.Color

object Constants {

    const val BASE_DELIVERY_URL = "https://gamedev-il.tech/"
    const val BASE_DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/"
    const val BASE_ROADS_URL = "https://roads.googleapis.com/v1/"
    const val API_KEY = "AIzaSyBrDMz5sY5AG-8hH4HSNY1QwJXJNZE0Qrs"

    const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34

    const val REQUEST_CODE_LOCATION_PERMISSION = 0

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_MAIN_ACTIVITY = "ACTION_SHOW_MAIN_ACTIVITY"

    const val IS_FIRST_RUN = "IS_FIRST_RUN"

    const val TIMER_UPDATE_INTERVAL = 50L

    const val SHARED_PREFERENCES_NAME = "sharedPref"
    const val KEY_FIRST_TIME_TOGGLE = "KEY_FIRST_TIME_TOGGLE"
    const val KEY_NAME = "KEY_NAME"
    const val KEY_WEIGHT = "KEY_WEIGHT"

    const val LOCATION_UPDATE_INTERVAL = 2000L
    const val FASTEST_LOCATION_INTERVAL = 2000L


    const val MAP_ZOOM = 18f

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1
}