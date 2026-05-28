package com.ikaroorg.pomodoro_app.data.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    var isComplete: Boolean = false
)