package com.ajinkya.weather_forecast.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ajinkya.weather_forecast.data.DataOrException
import com.ajinkya.weather_forecast.model.WeatherModel
import com.ajinkya.weather_forecast.navigation.WeatherScreens
import com.ajinkya.weather_forecast.screens.main.MainViewModel
import com.ajinkya.weather_forecast.util.Constants
import com.ajinkya.weather_forecast.util.formatDate
import com.ajinkya.weather_forecast.util.formatDecimals
import com.ajinkya.weather_forecast.widgets.*

private const val TAG = "MainScreen"

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    cityName: String? = ""
) {
    Log.e(TAG, "MainScreen: cityName: $cityName")

    val weatherData = produceState<DataOrException<WeatherModel, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = cityName ?: "")
    }.value

    if (weatherData.loading == true) {
        Log.e(TAG, "ShowData: InLoading")
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }
}

@Composable
fun MainScaffold(weatherModel: WeatherModel, navController: NavController) {

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weatherModel.city.name + ", " + weatherModel.city.country,
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)

                }

            ) {
                Log.d(TAG, "MainScaffold: back Button Clicked")
            }
        }) {
        MainContent(weatherModel)

    }

}

@Composable
fun MainContent(weatherModel: WeatherModel) {
    val weatherItem = weatherModel.list[0]
    val imageUrl = Constants.IMAGE_BASE_URL + weatherItem.weather[0].icon + ".png"
    val weatherType = weatherItem.weather[0].main
    val weatherDay = weatherItem.temp.day

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherItem.dt), style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )


        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WeatherStateImage(imageUrl)

                Text(
                    text = formatDecimals(weatherDay) + "\u2103",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weatherType, fontStyle = FontStyle.Italic)

            }

        }
        HumidityWindPressureRow(weatherItem)
        Divider()
        SunsetAndSunriseRow(weatherItem)
        Text(
            text = "This Week", modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = MaterialTheme.colors.primary
        ) {
            LazyColumn(modifier = Modifier.padding(5.dp), contentPadding = PaddingValues(2.dp)) {
                items(items = weatherModel.list) { singleItem ->
                    WeatherDetailsRow(singleItem)
                }
            }

        }
    }
}