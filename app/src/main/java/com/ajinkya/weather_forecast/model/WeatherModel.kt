package com.ajinkya.weather_forecast.model

data class WeatherModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Double
)