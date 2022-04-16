package com.ajinkya.weather_forecast.repository

import android.util.Log
import com.ajinkya.weather_forecast.data.DataOrException
import com.ajinkya.weather_forecast.model.WeatherModel
import com.ajinkya.weather_forecast.network.WeatherAPI
import javax.inject.Inject


private const val TAG = "WeatherRepository"
class WeatherRepository @Inject constructor(private val api: WeatherAPI) {
    suspend fun getWeatherData(cityName: String): DataOrException<WeatherModel, Boolean, Exception> {
        val res = try {
            api.getWeatherJsonData(cityName)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "getWeatherData: ${e.message}")
            return DataOrException(exception = e)
        }
        return DataOrException(data = res)
    }
}