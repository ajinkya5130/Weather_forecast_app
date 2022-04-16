package com.ajinkya.weather_forecast.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 5.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    TopAppBar(modifier = Modifier.padding(5.dp), elevation = elevation,
        title = {
            Text(
                text = title,
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
            )
        }, actions = {
            if (isMainScreen) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More option Icon"
                    )
                }

            } else {
                Box {}
            }
        }, backgroundColor = Color.Transparent,
        navigationIcon = {
            if (icon != null) {
                IconButton(onClick = { onButtonClicked.invoke() }) {
                    Icon(
                        imageVector = icon, contentDescription = "BackButton",
                        tint = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
            }
        }
    )/* {

        
    }*/
}