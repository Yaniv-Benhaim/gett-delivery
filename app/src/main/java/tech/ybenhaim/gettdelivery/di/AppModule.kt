package tech.ybenhaim.gettdelivery.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.ybenhaim.gettdelivery.data.db.LocationDao
import tech.ybenhaim.gettdelivery.data.db.LocationDatabase
import tech.ybenhaim.gettdelivery.data.remote.api.DeliveryApi
import tech.ybenhaim.gettdelivery.data.remote.api.DirectionsApi
import tech.ybenhaim.gettdelivery.repository.GettRepository
import tech.ybenhaim.gettdelivery.util.Constants.BASE_DELIVERY_URL
import tech.ybenhaim.gettdelivery.util.Constants.BASE_DIRECTIONS_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesGettRepository(
        api: DeliveryApi, directionsApi: DirectionsApi, locationDao: LocationDao
    ) = GettRepository(api, directionsApi, locationDao)

    @Singleton
    @Provides
    fun provideDeliveryApi(): DeliveryApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_DELIVERY_URL)
            .build()
            .create(DeliveryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDirectionsApi(): DirectionsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_DIRECTIONS_URL)
            .build()
            .create(DirectionsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocationDatabase =
        LocationDatabase.create(context)

    @Provides
    fun provideDao(database: LocationDatabase): LocationDao {
        return database.locationDao()
    }

}