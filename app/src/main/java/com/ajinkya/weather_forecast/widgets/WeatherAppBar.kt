package com.ajinkya.weather_forecast.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ajinkya.weather_forecast.navigation.WeatherScreens

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
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog, navController)
    }

    TopAppBar(modifier = Modifier.padding(5.dp), elevation = elevation,
        title = {
            Text(
                text = title,
                color = Color.Black,
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        }, actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
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

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    val menuItems = listOf<String>("About", "Favorite", "Settings")
    var expanded by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.TopEnd)
            .absolutePadding(left = 45.dp, right = 20.dp)
    ) {

        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(150.dp)
                .background(color = Color.White)
        ) {
            menuItems.forEachIndexed { index: Int, text: String ->
                DropdownMenuItem(onClick = {
                    showDialog.value = false
                    expanded = false
                }) {
                    Icon(
                        imageVector =
                        when (text) {
                            "About" -> Icons.Default.Info
                            "Favorite" -> Icons.Default.FavoriteBorder
                            "Settings" -> Icons.Default.Settings
                            else -> Icons.Default.Info
                        }, contentDescription = "DipDownIcon",
                        tint = Color.Magenta
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(text = text, modifier = Modifier.clickable {
                        navController.navigate(
                            when (text) {
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favorite" -> WeatherScreens.FavoriteScreen.name
                                else -> WeatherScreens.SettingsScreen.name
                            }
                        )

                    }, fontWeight = FontWeight.W300)

                }
            }

        }

    }

}
