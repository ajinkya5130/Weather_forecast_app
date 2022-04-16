package com.ajinkya.weather_forecast.di

import com.ajinkya.weather_forecast.network.WeatherAPI
import com.ajinkya.weather_forecast.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun retrofitInstance(): WeatherAPI {
        /* val interceptor = HttpLoggingInterceptor()
         interceptor.level = (HttpLoggingInterceptor.Level.BODY)
         val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()*/

        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            // .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

}
