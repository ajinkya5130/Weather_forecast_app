package com.ajinkya.weather_forecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ajinkya.weather_forecast.model.FavoriteModel
import com.ajinkya.weather_forecast.navigation.WeatherScreens
import com.ajinkya.weather_forecast.screens.favorite.FavoriteViewModel

//@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 5.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showIt = remember {
        mutableStateOf(false)
    }
    val context: Context = LocalContext.current

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
            if (isMainScreen) {
                val isAlreadyPresentInDB =
                    favoriteViewModel.favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }
                if (isAlreadyPresentInDB.isNullOrEmpty()) {
                    IconButton(onClick = {
                        val split = title.split(",")
                        favoriteViewModel.insertFav(
                            FavoriteModel(
                                city = split[0], country = split[1]
                            )
                        ).run {
                            showIt.value = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Fav Button",
                            tint = Color.Red.copy(alpha = 0.6f),
                            modifier = Modifier
                                .scale(0.9f)
                        )
                    }
                } else {


                    IconButton(onClick = {
                        val split = title.split(",")
                        favoriteViewModel.insertFav(
                            FavoriteModel(
                                city = split[0], country = split[1]
                            )
                        ).run {
                            showIt.value = false
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Favorite, contentDescription = "Fav Button",
                            tint = Color.Red.copy(alpha = 0.6f),
                            modifier = Modifier
                                .scale(0.9f)
                        )
                    }
                }
                ShowToast(context, showIt)
            }
        }
    )/* {

        
    }*/
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {

    if (showIt.value) {
        Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show()
    }

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
