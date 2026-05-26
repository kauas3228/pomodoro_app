package com.ikaroorg.pomodoro_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ikaroorg.pomodoro_app.ui.screen.HomeScreen
import com.ikaroorg.pomodoro_app.ui.screen.SettingsScreen

@Composable
fun AppRoot() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home"){
            composable("home"){
                HomeScreen(
                    navController = navController
                )
            }
            composable("settings"){
                SettingsScreen(
                    navController = navController
                )
            }
    }
}