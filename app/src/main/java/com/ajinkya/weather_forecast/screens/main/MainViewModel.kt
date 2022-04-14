package com.ajinkya.weather_forecast.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajinkya.weather_forecast.data.DataOrException
import com.ajinkya.weather_forecast.model.WeatherObject
import com.ajinkya.weather_forecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    val data: MutableState<DataOrException<WeatherObject, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))


    init {
        loadWeather()
    }

    private fun loadWeather() {

        getWeather("Delhi")
    }

    private fun getWeather(city: String) {

        viewModelScope.launch {
            if (city.isEmpty()) return@launch

            data.value.loading = true
            data.value = repository.getWeatherData(cityName = city)
            if (data.value.data.toString().isNotEmpty())
                data.value.loading = false

        }
        Log.e(TAG, "getWeather: ${data.value.data.toString()}")
    }
}