package tech.ybenhaim.gettdelivery.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.ybenhaim.gettdelivery.data.remote.api.DeliveryApi
import tech.ybenhaim.gettdelivery.repository.GettRepository
import tech.ybenhaim.gettdelivery.util.Constants.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesGettRepository(
        api: DeliveryApi
    ) = GettRepository(api)

    @Singleton
    @Provides
    fun provideDeliveryApi(): DeliveryApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DeliveryApi::class.java)
    }

}