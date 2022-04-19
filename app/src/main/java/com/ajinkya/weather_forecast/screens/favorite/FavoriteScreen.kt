package com.ajinkya.weather_forecast.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ajinkya.weather_forecast.model.FavoriteModel
import com.ajinkya.weather_forecast.navigation.WeatherScreens
import com.ajinkya.weather_forecast.widgets.WeatherAppBar

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        WeatherAppBar(
            "Favorite cities",
            isMainScreen = false, navController = navController,
            icon = Icons.Default.ArrowBack
        ) { navController.popBackStack() }
    }) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favoriteViewModel.favList.collectAsState().value
                LazyColumn {
                    items(items = list) {
                        CityRow(it, navController = navController, favoriteViewModel)
                    }
                }
            }

        }

    }
}

@Composable
fun CityRow(
    fav: FavoriteModel,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name + "/${fav.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(10.dp), topStart = CornerSize(10.dp)),
        color = Color(0xFF8FB8DB)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = fav.city, modifier = Modifier.padding(start = 5.dp))
            Surface(
                modifier = Modifier.padding(3.dp),
                shape = CircleShape,
                color = Color.Magenta
            ) {
                Text(
                    text = fav.country, modifier = Modifier.padding(start = 2.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            IconButton(onClick = { favoriteViewModel.deleteFav(fav) }) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "Delete Button",
                    tint = Color.Red.copy(alpha = 0.3f),
                    modifier = Modifier
                        .scale(0.9f)
                )
            }
        }

    }

}
