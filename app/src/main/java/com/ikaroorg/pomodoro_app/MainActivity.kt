package com.ikaroorg.pomodoro_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ikaroorg.pomodoro_app.navigation.AppRoot
import com.ikaroorg.pomodoro_app.ui.screen.HomeScreen
import com.ikaroorg.pomodoro_app.ui.theme.Pomodoro_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pomodoro_appTheme {
                AppRoot()
            }
        }
    }
}