package tech.ybenhaim.gettdelivery.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

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