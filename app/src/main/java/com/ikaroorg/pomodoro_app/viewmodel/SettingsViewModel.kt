package com.ikaroorg.pomodoro_app.viewmodel

import com.ikaroorg.pomodoro_app.data.local.UserPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    val focusTime = userPreferences.focusTime.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = 25
    )

    val shortPause = userPreferences.shortPause.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = 5
    )

    val longPause = userPreferences.longPause.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = 15
    )

    val useSound = userPreferences.useSound.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = true
    )

    val useVibrate = userPreferences.useVibrate.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = true
    )

    val keepScreenOn = userPreferences.keepScreenOn.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = false
    )

    fun updateFocusTime(minutes: Int) {
        viewModelScope.launch {
            userPreferences.saveFocusTime(minutes)
        }
    }

    fun updateShortPause(minutes: Int) {
        viewModelScope.launch {
            userPreferences.saveShortPause(minutes)
        }
    }

    fun updateLongPause(minutes: Int) {
        viewModelScope.launch {
            userPreferences.saveLongPause(minutes)
        }
    }

    fun toggleSound(useSound: Boolean) {
        viewModelScope.launch {
            userPreferences.saveUseSound(useSound)
        }
    }

    fun toggleVibrate(useVibrate: Boolean) {
        viewModelScope.launch {
            userPreferences.saveUseVibrate(useVibrate)
        }
    }

    fun toggleKeepScreenOn(keepScreenOn: Boolean) {
        viewModelScope.launch {
            userPreferences.saveKeepScreenOn(keepScreenOn)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    ?: throw IllegalStateException("Application context not found")

                SettingsViewModel(
                    userPreferences = UserPreferences(context)
                )
            }
        }
    }
}