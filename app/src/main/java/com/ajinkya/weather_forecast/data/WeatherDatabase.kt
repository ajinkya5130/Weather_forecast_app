package com.ajinkya.weather_forecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajinkya.weather_forecast.model.FavoriteModel

@Database(entities = [FavoriteModel::class], exportSchema = false, version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao


}