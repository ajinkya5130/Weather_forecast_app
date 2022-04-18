package com.ajinkya.weather_forecast.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.ajinkya.weather_forecast.R
import com.ajinkya.weather_forecast.data.DataOrException
import com.ajinkya.weather_forecast.model.WeatherItem
import com.ajinkya.weather_forecast.model.WeatherModel
import com.ajinkya.weather_forecast.screens.main.MainViewModel
import com.ajinkya.weather_forecast.util.Constants
import com.ajinkya.weather_forecast.util.formatDate
import com.ajinkya.weather_forecast.util.formatDateTime
import com.ajinkya.weather_forecast.util.formatDecimals
import com.ajinkya.weather_forecast.widgets.WeatherAppBar

private const val TAG = "MainScreen"

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {

    val weatherData = produceState<DataOrException<WeatherModel, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData("Delhi")
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
                //navController = navController
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
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "Weather image",
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun HumidityWindPressureRow(weatherModel: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity Icon",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = "${weatherModel.humidity}%",
                style = MaterialTheme.typography.caption
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {

            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = "${weatherModel.pressure} psi", style = MaterialTheme.typography.caption)


        }
        Row(modifier = Modifier.padding(4.dp)) {

            Icon(
                painter = painterResource(id = R.drawable.wind), contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.size(5.dp))
            Text(text = "${weatherModel.speed} m/h", style = MaterialTheme.typography.caption)

        }

    }

}

@Composable
fun SunsetAndSunriseRow(weatherModel: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = formatDateTime(weatherModel.sunrise),
                style = MaterialTheme.typography.caption
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {

            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = formatDateTime(weatherModel.sunset),
                style = MaterialTheme.typography.caption
            )


        }

    }

}

