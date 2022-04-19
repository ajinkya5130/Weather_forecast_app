package com.ajinkya.weather_forecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ajinkya.weather_forecast.screens.MainScreen
import com.ajinkya.weather_forecast.screens.WeatherSplashScreen
import com.ajinkya.weather_forecast.screens.about.AboutScreen
import com.ajinkya.weather_forecast.screens.favorite.FavoriteScreen
import com.ajinkya.weather_forecast.screens.main.MainViewModel
import com.ajinkya.weather_forecast.screens.search.SearchScreen
import com.ajinkya.weather_forecast.screens.settings.SettingScreen

@ExperimentalComposeUiApi
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

        val route = WeatherScreens.MainScreen.name

        composable(
            "$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                })
        ) { navBack ->
            navBack.arguments?.getString("city").let { cityName ->

                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(
                    navController = navController, mainViewModel,
                    cityName = cityName
                )
            }
        }
        composable(WeatherScreens.SearchScreen.name) {
            //val mainViewModel = hiltViewModel<MainViewModel>()
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            //val mainViewModel = hiltViewModel<MainViewModel>()
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            //val mainViewModel = hiltViewModel<MainViewModel>()
            SettingScreen(navController = navController)
        }
        composable(WeatherScreens.FavoriteScreen.name) {
            //val mainViewModel = hiltViewModel<MainViewModel>()
            FavoriteScreen(navController = navController)
        }


    }
}