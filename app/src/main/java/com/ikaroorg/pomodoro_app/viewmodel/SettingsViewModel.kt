package com.ikaroorg.pomodoro_app.viewmodel

import com.ikaroorg.pomodoro_app.data.local.DataStoreManager
import com.ikaroorg.pomodoro_app.data.model.Task
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    val focusTime = dataStoreManager.focusTime.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = 25
    )

    val shortPause = dataStoreManager.shortPause.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = 5
    )

    val longPause = dataStoreManager.longPause.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = 15
    )

    val useSound = dataStoreManager.useSound.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = true
    )

    val useVibrate = dataStoreManager.useVibrate.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = true
    )

    val keepScreenOn = dataStoreManager.keepScreenOn.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = false
    )

    fun updateFocusTime(minutes: Int) {
        viewModelScope.launch {
            dataStoreManager.saveFocusTime(minutes)
        }
    }

    fun updateShortPause(minutes: Int) {
        viewModelScope.launch {
            dataStoreManager.saveShortPause(minutes)
        }
    }

    fun updateLongPause(minutes: Int) {
        viewModelScope.launch {
            dataStoreManager.saveLongPause(minutes)
        }
    }

    fun toggleSound(useSound: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveUseSound(useSound)
        }
    }

    fun toggleVibrate(useVibrate: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveUseVibrate(useVibrate)
        }
    }

    fun toggleKeepScreenOn(keepScreenOn: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveKeepScreenOn(keepScreenOn)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    ?: throw IllegalStateException("Application context not found")

                SettingsViewModel(
                    dataStoreManager = DataStoreManager(context)
                )
            }
        }
    }
}