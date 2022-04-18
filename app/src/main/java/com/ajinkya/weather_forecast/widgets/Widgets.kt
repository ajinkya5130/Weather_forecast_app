package com.ajinkya.weather_forecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.ajinkya.weather_forecast.R
import com.ajinkya.weather_forecast.model.WeatherItem
import com.ajinkya.weather_forecast.util.Constants
import com.ajinkya.weather_forecast.util.formatDate
import com.ajinkya.weather_forecast.util.formatDateTime
import com.ajinkya.weather_forecast.util.formatDecimals


@Composable
fun WeatherDetailsRow(singleItem: WeatherItem) {
    val imageUrl = Constants.IMAGE_BASE_URL + singleItem.weather[0].icon + ".png"
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(5.dp), bottomStart = CornerSize(5.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = formatDate(singleItem.dt).split(",")[0], modifier = Modifier.padding(2.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape, color = Color.Magenta
            ) {
                Text(
                    color = Color.White,
                    text = singleItem.weather[0].description,
                    modifier = Modifier.padding(5.dp),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.caption
                )

            }

            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(formatDecimals(singleItem.temp.max) + "\u2103")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray.copy(alpha = 0.7f)
                    )
                ) {
                    append(formatDecimals(singleItem.temp.min) + "\u2103")
                }
            }, modifier = Modifier.padding(2.dp))


        }

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

