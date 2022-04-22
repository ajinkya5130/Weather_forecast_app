package com.ajinkya.weather_forecast.screens.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ajinkya.weather_forecast.model.Unit
import com.ajinkya.weather_forecast.widgets.WeatherAppBar

private const val TAG = "SettingScreen"

@Composable
fun SettingScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {

    var unitToggleChanged by remember {
        mutableStateOf(false)
    }

    val measurementUnits = listOf("Imperial(F)", "Metric(C)")
    val choiceFromDb = settingViewModel.unitList.collectAsState().value
    val defaultChoice =
        if (choiceFromDb.isNullOrEmpty()) measurementUnits[0] else choiceFromDb[0].unit
    val choiceState = remember {
        mutableStateOf(defaultChoice)
    }
    Log.e(
        TAG,
        "\nSettingScreen: $choiceFromDb\n defaultChoice: $defaultChoice\nchoiceState: ${choiceState.value}"
    )

    Scaffold(
        topBar = {
            WeatherAppBar(
                "Settings",
                isMainScreen = false, navController = navController,
                icon = Icons.Default.ArrowBack
            ) { navController.popBackStack() }
        }
    ) {

        androidx.compose.material.Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = "Change Units of measurement",
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                IconToggleButton(
                    checked = !unitToggleChanged, onCheckedChange = {
                        unitToggleChanged = !it
                        if (unitToggleChanged) {
                            choiceState.value = measurementUnits[1]
                        } else {
                            choiceState.value = measurementUnits[0]

                        }
                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(8.dp)
                        .background(color = Color.LightGray)
                ) {

                    Text(text = if (unitToggleChanged) "Fahrenheit \u2109" else "Celsius \u2103")
                }
                Button(
                    onClick = {
                        settingViewModel.deleteAllUnits()
                        settingViewModel.insertUnit(Unit(choiceState.value))
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(.4f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF6FF7D7)
                    )
                ) {
                    Text(
                        text = "Save",
                        Modifier.padding(5.dp),
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )

                }

            }

        }

    }
}