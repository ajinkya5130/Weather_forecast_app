package com.ajinkya.weather_forecast.model

data class WeatherObject(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)