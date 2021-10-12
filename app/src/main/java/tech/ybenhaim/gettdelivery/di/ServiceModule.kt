package tech.ybenhaim.gettdelivery.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import tech.ybenhaim.gettdelivery.MainActivity
import tech.ybenhaim.gettdelivery.R
import tech.ybenhaim.gettdelivery.util.Constants

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {
//
//    @ServiceScoped
//    @Provides
//    fun provideFusedLocationProviderClient(
//        @ApplicationContext app: Context
//    ) = FusedLocationProviderClient(app)
//
//    @ServiceScoped
//    @Provides
//    fun provideMainActivityPendingIntent(
//        @ApplicationContext app: Context
//    ) = PendingIntent.getActivity(
//        app,
//        0,
//        Intent(app, MainActivity::class.java).also {
//            it.action = Constants.ACTION_SHOW_TRACKING_FRAGMENT
//        },
//        PendingIntent.FLAG_UPDATE_CURRENT
//    )
//
//    @ServiceScoped
//    @Provides
//    fun provideBaseNotificationBuilder(
//        @ApplicationContext app: Context,
//        pendingIntent: PendingIntent
//    ) = NotificationCompat.Builder(app, Constants.NOTIFICATION_CHANNEL_ID)
//        .setAutoCancel(false)
//        .setOngoing(true)
//        .setSmallIcon(R.drawable.gett_logo)
//        .setContentTitle("Gett Delivery")
//        .setContentText("Tel Aviv")
//        .setContentIntent(pendingIntent)
}