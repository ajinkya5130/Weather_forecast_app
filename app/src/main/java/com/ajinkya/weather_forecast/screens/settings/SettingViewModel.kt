package com.ajinkya.weather_forecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajinkya.weather_forecast.model.Unit
import com.ajinkya.weather_forecast.repository.WeatherDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SettingViewModel"

@HiltViewModel
class SettingViewModel @Inject constructor(private val repository: WeatherDatabaseRepository) :
    ViewModel() {
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())

    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect() {
                    if (it.isNullOrEmpty()) {
                        Log.d(TAG, ": empty list")
                    } else {
                        _unitList.value = it
                        Log.d(TAG, ":${it.toString()}")
                    }
                }
        }
    }

    fun insertUnit(unit: Unit) = viewModelScope.launch { repository.insertUnit(unit) }
    fun updateUnit(unit: Unit) = viewModelScope.launch { repository.updateUnit(unit) }
    fun deleteUnit(unit: Unit) = viewModelScope.launch { repository.deleteUnit(unit) }
    fun deleteAllUnits() = viewModelScope.launch { repository.deleteAllUnits() }

}