package com.ajinkya.weather_forecast.repository

import com.ajinkya.weather_forecast.data.DataOrException
import com.ajinkya.weather_forecast.model.WeatherObject
import com.ajinkya.weather_forecast.network.WeatherAPI
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val api: WeatherAPI) {
    suspend fun getWeatherData(cityName: String): DataOrException<WeatherObject, Boolean, Exception> {
        val res = try {
            api.getWeatherJsonData(cityName)
        } catch (e: Exception) {
            return DataOrException(exception = e)
        }
        return DataOrException(data = res)
    }
}