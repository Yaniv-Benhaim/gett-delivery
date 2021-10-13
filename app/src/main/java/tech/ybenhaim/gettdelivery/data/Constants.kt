package tech.ybenhaim.gettdelivery.data

import androidx.compose.ui.graphics.Color

object Constants {

    //Networking
    const val BASE_DELIVERY_URL = "https://gamedev-il.tech/"
    const val BASE_DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/"
    const val BASE_ROADS_URL = "https://roads.googleapis.com/v1/"

    //Location Tracking
    const val API_KEY = "AIzaSyBrDMz5sY5AG-8hH4HSNY1QwJXJNZE0Qrs"
    const val REQUEST_CODE_LOCATION_PERMISSION = 0
    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_MAIN_ACTIVITY = "ACTION_SHOW_MAIN_ACTIVITY"
    const val IS_FIRST_RUN = "IS_FIRST_RUN"
    const val LOCATION_UPDATE_INTERVAL = 500L
    const val FASTEST_LOCATION_INTERVAL = 500L

    //Notifications
    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1

    //Google Maps
    const val MAP_ZOOM = 18f

    //Deliveries
    const val NAVIGATE_TO_PICKUP = "navigate_to_pickup"
    const val PICKUP_PARCELS = "pickup"
    const val NAVIGATE_TO_DROP_OFF = "navigate_to_drop_off"
    const val DROP_OFF_PARCELS = "drop_off"
    const val FINISHED_TASKS = "Amazing you delivered everything"
    const val ARRIVED_BUTTON_TEXT = "Arrived"

    const val NAVIGATE_TO_PICKUP_TITLE = "Navigate to pickup"
    const val PICKUP_PARCELS_TITLE = "Pickup"
    const val NAVIGATE_TO_DROP_OFF_TITLE = "Navigate to drop off"
    const val DROP_OFF_PARCELS_TITLE = "Drop off"

    const val PARCELS_TO_COLLECT = " Parcels to collect"
    const val PARCELS_TO_DELIVER = " Parcels to deliver"

    //Debug tags
    const val TAG_DELIVERY = "DELIVERY"
    const val TAG_LOCATION_TRACKING = "DELIVERY"
    const val TAG_NETWORK_REQUESTS = "NETWORK_REQUESTS"
    const val TAG_FLOW_COLLECTION = "FLOW_COLLECTION"
    const val TAG_FLOW_EMISSION = "FLOW_EMISSION"
    const val TAG_GOOGLE_MAPS = "TAG_GOOGLE_MAPS"

    //Navigation routes
    const val SPLASH_SCREEN = "splash"
    const val HOME_SCREEN = "home"
    const val PROFILE_SCREEN = "profile"
    const val SETTINGS_SCREEN = "settings"
    const val PERMISSIONS_SCREEN = "permissions"
    const val PICKUP_SCREEN = "pickup"
    const val HISTORY_SCREEN = "history"



}