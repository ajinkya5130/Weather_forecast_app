package com.ajinkya.weather_forecast.repository

import com.ajinkya.weather_forecast.data.WeatherDao
import com.ajinkya.weather_forecast.model.FavoriteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherDatabaseRepository @Inject constructor(private val dao: WeatherDao) {

    fun getFavorites(): Flow<List<FavoriteModel>> = dao.getFavorites()
    suspend fun getFavById(city: String) = dao.getFavById(city)
    suspend fun insertFav(favoriteModel: FavoriteModel) =
        dao.insertFavorite(favoriteModel = favoriteModel)

    suspend fun deleteFav(favoriteModel: FavoriteModel) =
        dao.deleteFav(favoriteModel = favoriteModel)

    suspend fun updateFav(favoriteModel: FavoriteModel) =
        dao.updateFavorite(favoriteModel = favoriteModel)

    suspend fun deleteAllFav() = dao.deleteAllFav()


}