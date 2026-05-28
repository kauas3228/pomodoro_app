package com.ikaroorg.pomodoro_app.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ikaroorg.pomodoro_app.ui.screen.HomeScreen
import com.ikaroorg.pomodoro_app.ui.screen.SettingsScreen
import com.ikaroorg.pomodoro_app.viewmodel.SettingsViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppRoot() {
    val navController = rememberNavController()
    val viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
    NavHost(navController = navController, startDestination = "home"){
            composable("home"){
                HomeScreen(
                    navController = navController,
                )
            }
            composable("settings"){
                SettingsScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
    }
}