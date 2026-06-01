package com.ikaroorg.pomodoro_app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isComplete: Boolean = false
)