package com.ajinkya.weather_forecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajinkya.weather_forecast.model.FavoriteModel
import com.ajinkya.weather_forecast.model.Unit

@Database(entities = [FavoriteModel::class, Unit::class], exportSchema = false, version = 2)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}