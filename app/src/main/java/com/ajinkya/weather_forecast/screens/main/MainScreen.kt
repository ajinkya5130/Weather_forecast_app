package com.ajinkya.weather_forecast.screens

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ajinkya.weather_forecast.data.DataOrException
import com.ajinkya.weather_forecast.model.WeatherModel
import com.ajinkya.weather_forecast.screens.main.MainViewModel
import com.ajinkya.weather_forecast.widgets.WeatherAppBar

private const val TAG = "MainScreen"

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {

    val weatherData = produceState<DataOrException<WeatherModel, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData("Portland")
    }.value

    if (weatherData.loading == true) {
        Log.e(TAG, "ShowData: InLoading")
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        Log.e(TAG, "ShowData: Load Complete")

        //Text(text = "Main Screen ${weatherData.data?.city?.country}")
        MainScaffold(weatherData.data!!, navController)
    }


}

@Composable
fun MainScaffold(weatherModel: WeatherModel, navController: NavController) {

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weatherModel.city.name + ", " + weatherModel.city.country,
                navController = navController
                /* , icon = Icons.Default.ArrowBack*/
            ) {
                Log.d(TAG, "MainScaffold: back Button Clicked")
            }
        }) {
        MainContent(weatherModel)

    }

}

@Composable
fun MainContent(weatherModel: WeatherModel) {

    Text(text = weatherModel.city.name)

}
