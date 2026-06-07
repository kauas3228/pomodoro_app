package com.ikaroorg.pomodoro_app.viewmodel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ikaroorg.pomodoro_app.R
import com.ikaroorg.pomodoro_app.data.local.DataStoreManager
import com.ikaroorg.pomodoro_app.data.model.Task
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

enum class PomodoroSession {
    FOCUS, SHORT_BREAK, LONG_BREAK
}

class HomeViewModel(
    application: Application,
    private val settingsViewModel: SettingsViewModel,
    private val dataStoreManager: DataStoreManager
) : AndroidViewModel(application) {

    var timerValue by mutableLongStateOf(25 * 60L)
        private set

    var isRunning by mutableStateOf(false)
        private set

    var currentSession by mutableStateOf(PomodoroSession.FOCUS)
        private set

    var focusCycles by mutableIntStateOf(0)
        private set

    var useSoundValue by mutableStateOf(true)
        private set
    var useVibrateValue by mutableStateOf(true)
        private set
    var showAlarmDialog by mutableStateOf(false)
        private set

    val tasks: StateFlow<List<Task>> = dataStoreManager.tasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = emptyList()
    )

    private var timerJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null
    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getApplication<Application>().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getApplication<Application>().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    init {
        viewModelScope.launch {
            settingsViewModel.focusTime.collectLatest { minutes ->
                if (!isRunning && currentSession == PomodoroSession.FOCUS && !showAlarmDialog) {
                    timerValue = minutes * 60L
                }
            }
        }
        viewModelScope.launch {
            settingsViewModel.shortPause.collectLatest { minutes ->
                if (!isRunning && currentSession == PomodoroSession.SHORT_BREAK && !showAlarmDialog) {
                    timerValue = minutes * 60L
                }
            }
        }
        viewModelScope.launch {
            settingsViewModel.longPause.collectLatest { minutes ->
                if (!isRunning && currentSession == PomodoroSession.LONG_BREAK && !showAlarmDialog) {
                    timerValue = minutes * 60L
                }
            }
        }
        viewModelScope.launch {
            settingsViewModel.useSound.collectLatest { useSound ->
                useSoundValue = useSound
            }
        }
        viewModelScope.launch {
            settingsViewModel.useVibrate.collectLatest { useVibrate ->
                useVibrateValue = useVibrate
            }
        }
    }

    fun toggleTimer() {
        if (isRunning) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        if (timerValue <= 0) {
            onTimerFinished()
            return
        }
        isRunning = true
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (timerValue > 0) {
                delay(1000)
                timerValue--
            }
            onTimerFinished()
        }
    }

    private fun pauseTimer() {
        isRunning = false
        timerJob?.cancel()
    }

    fun stopTimer() {
        pauseTimer()
        resetTimer()
    }

    private fun onTimerFinished() {
        isRunning = false
        timerJob?.cancel()
        
        if (useSoundValue || useVibrateValue) {
            if (useSoundValue) startAlarm()
            if (useVibrateValue) startVibration()
            showAlarmDialog = true
        } else {
            skipSession()
        }
    }

    private fun startAlarm() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(getApplication(), R.raw.alarm_sound).apply {
            isLooping = true
            start()
        }
    }

    private fun startVibration() {
        val timings = longArrayOf(0, 500, 200, 500)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(timings, 0))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(timings, 0)
        }
    }

    private fun stopVibration() {
        vibrator.cancel()
    }

    fun stopAlarmAndVibrateAndNextSession() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        stopVibration()
        showAlarmDialog = false

        skipSession()
    }

    fun skipSession() {
        isRunning = false
        timerJob?.cancel()

        if (currentSession == PomodoroSession.FOCUS) {
            focusCycles++
            if (focusCycles >= 4) {
                setSession(PomodoroSession.LONG_BREAK)
            } else {
                setSession(PomodoroSession.SHORT_BREAK)
            }
        } else {
            if (currentSession == PomodoroSession.LONG_BREAK) {
                focusCycles = 0
            }
            setSession(PomodoroSession.FOCUS)
        }
    }

    fun resetTimer() {
        pauseTimer()
        timerValue = when (currentSession) {
            PomodoroSession.FOCUS -> settingsViewModel.focusTime.value * 60L
            PomodoroSession.SHORT_BREAK -> settingsViewModel.shortPause.value * 60L
            PomodoroSession.LONG_BREAK -> settingsViewModel.longPause.value * 60L
        }
    }

    fun setSession(session: PomodoroSession) {
        pauseTimer()
        currentSession = session
        timerValue = when (session) {
            PomodoroSession.FOCUS -> settingsViewModel.focusTime.value * 60L
            PomodoroSession.SHORT_BREAK -> settingsViewModel.shortPause.value * 60L
            PomodoroSession.LONG_BREAK -> settingsViewModel.longPause.value * 60L
        }
    }

    fun formatTime(): String {
        val minutes = timerValue / 60
        val seconds = timerValue % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    fun addTask(title: String) {
        val currentTasks = tasks.value.toMutableList()
        val newTask = Task(
            id = UUID.randomUUID().toString(),
            title = title,
            description = ""
        )
        currentTasks.add(newTask)
        updateTasks(currentTasks)
    }

    fun deleteTask(taskId: String) {
        val currentTasks = tasks.value.toMutableList()
        currentTasks.removeAll { it.id == taskId }
        updateTasks(currentTasks)
    }

    fun updateTasks(tasks: List<Task>) {
        viewModelScope.launch {
            dataStoreManager.saveTasks(tasks)
        }
    }

    fun toggleTaskCompletion(taskId: String) {
        val currentTasks = tasks.value.toMutableList()
        val index = currentTasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val task = currentTasks[index]
            currentTasks[index] = task.copy(isComplete = !task.isComplete)
            updateTasks(currentTasks)
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
        stopVibration()
    }

    companion object {
        fun provideFactory(settingsViewModel: SettingsViewModel): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    ?: throw IllegalStateException("Application context not found")
                val application = context
                HomeViewModel(
                    application = application,
                    settingsViewModel = settingsViewModel,
                    dataStoreManager = DataStoreManager(application)
                )
            }
        }
    }
}
