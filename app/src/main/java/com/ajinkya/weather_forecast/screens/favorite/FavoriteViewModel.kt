package com.ajinkya.weather_forecast.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajinkya.weather_forecast.model.FavoriteModel
import com.ajinkya.weather_forecast.repository.WeatherDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FavoriteViewModel"

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val dbRepository: WeatherDatabaseRepository) :
    ViewModel() {

    private val _favList = MutableStateFlow<List<FavoriteModel>>(emptyList())

    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getFavorites().distinctUntilChanged().collect() {
                if (it.isNullOrEmpty()) {
                    Log.d(TAG, ": empty list")
                } else {
                    _favList.value = it
                    Log.d(TAG, ":${it.toString()}")
                }
            }
        }
    }

    fun insertFav(favoriteModel: FavoriteModel) = viewModelScope.launch {
        dbRepository.insertFav(favoriteModel)
    }

    fun updateFav(favoriteModel: FavoriteModel) = viewModelScope.launch {
        dbRepository.updateFav(favoriteModel = favoriteModel)
    }

    fun deleteFav(favoriteModel: FavoriteModel) = viewModelScope.launch {
        dbRepository.deleteFav(favoriteModel = favoriteModel)
    }


}