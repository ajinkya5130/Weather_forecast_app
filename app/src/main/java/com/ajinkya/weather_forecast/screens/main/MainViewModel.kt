package com.ajinkya.weather_forecast.screens.main

import androidx.lifecycle.ViewModel
import com.ajinkya.weather_forecast.data.DataOrException
import com.ajinkya.weather_forecast.model.WeatherModel
import com.ajinkya.weather_forecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    suspend fun getWeatherData(city: String): DataOrException<WeatherModel, Boolean, Exception> {
        return repository.getWeatherData(cityName = city)
    }
}