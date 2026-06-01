package com.ikaroorg.pomodoro_app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ikaroorg.pomodoro_app.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {
    companion object {
        val FOCUS_TIME = intPreferencesKey("focus_time")
        val SHORT_PAUSE = intPreferencesKey("short_pause")
        val LONG_PAUSE = intPreferencesKey("long_pause")
        val USE_SOUND = booleanPreferencesKey("use_sound")
        val USE_VIBRATE = booleanPreferencesKey("use_vibrate")
        val KEEP_SCREEN_ON = booleanPreferencesKey("keep_screen_on")
        val TASKS = stringPreferencesKey("tasks")
    }

    val focusTime: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[FOCUS_TIME] ?: 25
    }

    val shortPause: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SHORT_PAUSE] ?: 5
    }

    val longPause: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[LONG_PAUSE] ?: 15
    }

    val useSound: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[USE_SOUND] ?: true
    }

    val useVibrate: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[USE_VIBRATE] ?: true
    }

    val keepScreenOn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEEP_SCREEN_ON] ?: false
    }

    val tasks: Flow<List<Task>> = context.dataStore.data.map { preferences ->
        val tasksJson = preferences[TASKS] ?: return@map emptyList()
        try {
            Json.decodeFromString<List<Task>>(tasksJson)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Suspend Functions

    suspend fun saveFocusTime(minutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[FOCUS_TIME] = minutes
        }
    }

    suspend fun saveShortPause(minutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[SHORT_PAUSE] = minutes
        }
    }

    suspend fun saveLongPause(minutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[LONG_PAUSE] = minutes
        }
    }

    suspend fun saveUseSound(useSound: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USE_SOUND] = useSound
        }
    }

    suspend fun saveUseVibrate(useVibrate: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USE_VIBRATE] = useVibrate
        }
    }

    suspend fun saveKeepScreenOn(keepScreenOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEEP_SCREEN_ON] = keepScreenOn
        }
    }

    suspend fun saveTasks(tasks: List<Task>) {
        context.dataStore.edit { preferences ->
            preferences[TASKS] = Json.encodeToString(tasks)
        }
    }
}