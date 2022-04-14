package com.ajinkya.weather_forecast.model

data class WeatherItem(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Weather>,
    val message: Double
)