package com.ajinkya.weather_forecast.data

import androidx.room.*
import com.ajinkya.weather_forecast.model.FavoriteModel
import com.ajinkya.weather_forecast.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM fav_table")
    fun getFavorites(): Flow<List<FavoriteModel>>

    @Query("Select * from fav_table where city=:city ")
    suspend fun getFavById(city: String): FavoriteModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteModel: FavoriteModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favoriteModel: FavoriteModel)

    @Query("Delete from fav_table")
    suspend fun deleteAllFav()

    @Delete
    suspend fun deleteFav(favoriteModel: FavoriteModel)

    @Query("SELECT * FROM settings_table")
    fun getUnits(): Flow<List<Unit>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unitModel: Unit)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unitModel: Unit)

    @Query("Delete from settings_table")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unitModel: Unit)


}