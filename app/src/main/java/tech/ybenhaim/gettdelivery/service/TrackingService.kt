package tech.ybenhaim.gettdelivery.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tech.ybenhaim.gettdelivery.MainActivity
import tech.ybenhaim.gettdelivery.R
import tech.ybenhaim.gettdelivery.data.db.LocationDao
import tech.ybenhaim.gettdelivery.data.models.MyLocation
import tech.ybenhaim.gettdelivery.data.Constants.ACTION_PAUSE_SERVICE
import tech.ybenhaim.gettdelivery.data.Constants.ACTION_SHOW_MAIN_ACTIVITY
import tech.ybenhaim.gettdelivery.data.Constants.ACTION_START_OR_RESUME_SERVICE
import tech.ybenhaim.gettdelivery.data.Constants.ACTION_STOP_SERVICE
import tech.ybenhaim.gettdelivery.data.Constants.FASTEST_LOCATION_INTERVAL
import tech.ybenhaim.gettdelivery.data.Constants.LOCATION_UPDATE_INTERVAL
import tech.ybenhaim.gettdelivery.data.Constants.NOTIFICATION_CHANNEL_ID
import tech.ybenhaim.gettdelivery.data.Constants.NOTIFICATION_CHANNEL_NAME
import tech.ybenhaim.gettdelivery.data.Constants.NOTIFICATION_ID
import tech.ybenhaim.gettdelivery.data.Constants.TAG_LOCATION_TRACKING
import tech.ybenhaim.gettdelivery.util.tracking.TrackingUtility
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TrackingService : LifecycleService() {

/*
    This service is used for getting live updates of the users location.
    Service Flow:
    1 Receives command to start from onStartCommand.
    2 onCreate is called for initial setup & after calls updateLocationTracking Function
    3 startForeground function is called and creates a notification
    4 Location callback is created which inserts every location in a local database

    The reason i chose to insert new locations in a local database,
    is so that it's easier later on emit them as a flow of locations from one source of truth.
    also the app always has some history of the last points for when initial google maps setup.

 */

    @Inject
    lateinit var locationDao: LocationDao
    var isFirstRun = true
    var serviceKilled = false

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    //Keeping track of Locations received & to see if service is currently tracking or not
    companion object {
        val isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<MutableList<LatLng>>()
    }

    //Setting initial values
    private fun postInitialValues() {
        isTracking.value = false
        pathPoints.value = ArrayList()
    }

    @SuppressLint("VisibleForTests")
    override fun onCreate() {
        super.onCreate()

        Timber.tag(TAG_LOCATION_TRACKING).d( "Service onCreate")
        postInitialValues()
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        isTracking.observe(this) {
            updateLocationTracking(it)
        }

    }

    private fun killService() {

        Timber.tag(TAG_LOCATION_TRACKING).d( "Service killed")
        serviceKilled = true
        isFirstRun = true
        pauseService()
        postInitialValues()
        stopForeground(true)
        stopSelf()
    }

    private fun pauseService() {
        isTracking.postValue(false)
    }

    //Permission was already checked before
    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if(isTracking) {
            if(TrackingUtility.hasLocationPermissions(this)) {

                val request = LocationRequest.create().apply {
                    interval = LOCATION_UPDATE_INTERVAL
                    fastestInterval = FASTEST_LOCATION_INTERVAL
                    priority = PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if(isTracking.value!!) {
                result.locations.let { locations ->
                    locations.forEach { location ->
                        addPathPoint(location)
                        val currentLocation = MyLocation(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            bearing = location.bearing,
                            speed = location.speed
                        )
                        lifecycleScope.launch {
                            locationDao.insertLocation(currentLocation)
                        }
                    }
                }
            }
        }
    }

    private fun addPathPoint(location: Location?) {
        location?.let {
            val position = LatLng(it.latitude, it.longitude)
            pathPoints.value?.add(position)
        }
    }

    private fun startForegroundService() {
        try {
            isTracking.postValue(true)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(notificationManager)
            }

            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Gett Delivery")
                .setContentText("Active deliveries")
                .setContentIntent(getMainActivityPendingIntent())

            startForeground(NOTIFICATION_ID, notificationBuilder.build())

        } catch (e: Exception) {
            Timber.tag(TAG_LOCATION_TRACKING).e(e)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getMainActivityPendingIntent(): PendingIntent? {
        val activity = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java).also {
                it.action = ACTION_SHOW_MAIN_ACTIVITY
            },
            FLAG_UPDATE_CURRENT
        )
        return activity
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when(it.action) {

                ACTION_START_OR_RESUME_SERVICE -> {
                    if(isFirstRun) {
                        startForegroundService()
                        isFirstRun = false

                    } else {
                        Timber.tag(TAG_LOCATION_TRACKING).d( "Service is running")
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    pauseService()
                }
                ACTION_STOP_SERVICE -> {
                    killService()
                }

                else -> startForegroundService()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


}
