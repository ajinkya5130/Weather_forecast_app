package com.ajinkya.weather_forecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajinkya.weather_forecast.screens.MainScreen
import com.ajinkya.weather_forecast.screens.WeatherSplashScreen
import com.ajinkya.weather_forecast.screens.main.MainViewModel

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreens.name
    ) {
        composable(WeatherScreens.SplashScreens.name) {
            WeatherSplashScreen(navController = navController)
        }
        composable(WeatherScreens.MainScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, mainViewModel)
        }

    }
}