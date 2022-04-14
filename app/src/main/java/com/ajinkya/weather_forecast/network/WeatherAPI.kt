package com.ajinkya.weather_forecast.network

import com.ajinkya.weather_forecast.model.WeatherObject
import com.ajinkya.weather_forecast.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {

    @GET("data/2.5/forecast/daily")
    fun getWeatherJsonData(
        @Query("q") query: String,
        @Query("cnt") count: Int = 16,
        @Query("units") units: String = Constants.Units,
        @Query("appid") appID: String = Constants.api_key
    ): WeatherObject

}