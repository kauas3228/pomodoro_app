package com.ikaroorg.pomodoro_app.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ikaroorg.pomodoro_app.R
import com.ikaroorg.pomodoro_app.data.model.Task
import com.ikaroorg.pomodoro_app.viewmodel.HomeViewModel
import com.ikaroorg.pomodoro_app.viewmodel.PomodoroSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val tasks by viewModel.tasks.collectAsState()
    var isMenuExpanded by remember { mutableStateOf(false) }
    var showAddTaskDialog by remember { mutableStateOf(false) }
    var newTaskTitle by remember { mutableStateOf("") }
    var showDeleteTaskDialog by remember { mutableStateOf(false) }
    var tempDeleteTask by remember { mutableStateOf<Task?>(null) }

    val activeColor = when (viewModel.currentSession) {
        PomodoroSession.FOCUS -> MaterialTheme.colorScheme.primary
        PomodoroSession.SHORT_BREAK -> MaterialTheme.colorScheme.secondary
        PomodoroSession.LONG_BREAK -> MaterialTheme.colorScheme.tertiary
    }

    val onActiveColor = when (viewModel.currentSession) {
        PomodoroSession.FOCUS -> MaterialTheme.colorScheme.onPrimary
        PomodoroSession.SHORT_BREAK -> MaterialTheme.colorScheme.onSecondary
        PomodoroSession.LONG_BREAK -> MaterialTheme.colorScheme.onTertiary
    }

    if (viewModel.showAlarmDialog) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text(
                "Ciclo Finalizado!",
                    style = MaterialTheme.typography.titleLarge
            ) },
            text = { Text(
                "O tempo acabou. Deseja iniciar o próximo ciclo?",
                style= MaterialTheme.typography.labelLarge
            ) },
            confirmButton = {
                Button(
                    onClick = { viewModel.stopAlarmAndNextSession() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = activeColor
                    )
                ) {
                    Text("Próximo Ciclo")
                }
            }
        )
    }

    if (showAddTaskDialog) {
        AlertDialog(
            onDismissRequest = { showAddTaskDialog = false },
            title = { Text(
                "Nova Tarefa",
                style = MaterialTheme.typography.titleLarge
            ) },
            text = {
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = { Text(
                        "Título da tarefa",
                        style = MaterialTheme.typography.labelLarge
                    ) },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            viewModel.addTask(newTaskTitle)
                            newTaskTitle = ""
                            showAddTaskDialog = false
                            isMenuExpanded = false
                        }
                    }
                ) {
                    Text(
                        "Criar",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddTaskDialog = false }) {
                    Text(
                        "Cancelar",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }

    if(showDeleteTaskDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteTaskDialog = false },
            title = { Text(
                "Deseja excluir a tarefa?",
                style = MaterialTheme.typography.titleLarge
            ) },
            text = {
                Text(
                    "Essa ação não pode ser desfeita.",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if(tempDeleteTask != null){
                            viewModel.deleteTask(tempDeleteTask!!.id)
                            showDeleteTaskDialog = false
                            isMenuExpanded = false
                            tempDeleteTask = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(
                        "Deletar",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteTaskDialog = false
                    tempDeleteTask = null
                }) {
                    Text(
                        "Cancelar",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Pomodoro App",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Pomodoro App Logo",
                        modifier = Modifier.size(48.dp)
                    )
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("settings") },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.settings),
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AnimatedVisibility(
                    visible = isMenuExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Button(
                        onClick = { showAddTaskDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = activeColor)
                    ) {
                        Text("Criar Tarefa", color = onActiveColor)
                    }
                }
                FloatingActionButton(
                    onClick = { isMenuExpanded = !isMenuExpanded },
                    containerColor = activeColor,
                    contentColor = onActiveColor,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Menu de tarefas")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                TextButton(
                    onClick = { viewModel.setSession(PomodoroSession.FOCUS) },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (viewModel.currentSession == PomodoroSession.FOCUS) activeColor else Color.Transparent,
                        contentColor = if (viewModel.currentSession == PomodoroSession.FOCUS) onActiveColor else MaterialTheme.colorScheme.primary
                    ),
                    modifier = if (viewModel.currentSession != PomodoroSession.FOCUS) 
                        Modifier.border(
                            2.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(100)
                        )
                    else Modifier
                ) {
                    Text(
                        text = "Foco",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(Modifier.width(12.dp))
                TextButton(
                    onClick = { viewModel.setSession(PomodoroSession.SHORT_BREAK) },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (viewModel.currentSession == PomodoroSession.SHORT_BREAK) activeColor else Color.Transparent,
                        contentColor = if (viewModel.currentSession == PomodoroSession.SHORT_BREAK) onActiveColor else MaterialTheme.colorScheme.secondary
                    ),
                    modifier = if (viewModel.currentSession != PomodoroSession.SHORT_BREAK) 
                        Modifier.border(
                            2.dp,
                            MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(100)
                        )
                    else Modifier
                ) {
                    Text(
                        text = "Pausa Curta",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(Modifier.width(12.dp))
                TextButton(
                    onClick = { viewModel.setSession(PomodoroSession.LONG_BREAK) },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (viewModel.currentSession == PomodoroSession.LONG_BREAK) activeColor else Color.Transparent,
                        contentColor = if (viewModel.currentSession == PomodoroSession.LONG_BREAK) onActiveColor else MaterialTheme.colorScheme.tertiary
                    ),
                    modifier = if (viewModel.currentSession != PomodoroSession.LONG_BREAK) 
                        Modifier.border(
                            2.dp,
                            MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(100)
                        )
                    else Modifier
                ) {
                    Text(
                        text = "Pausa Longa",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            Spacer(Modifier.height(26.dp))
            Column(
                Modifier.width(280.dp)
                    .height(280.dp)
                    .border(8.dp, activeColor, CircleShape),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    viewModel.formatTime(),
                    style = MaterialTheme.typography.displayLarge,
                    color = activeColor
                )
            }
            Spacer(Modifier.height(12.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(4) { index ->
                    val color = if (index < viewModel.focusCycles) {
                        activeColor
                    } else {
                        MaterialTheme.colorScheme.outline
                    }
                    Box(
                        modifier = Modifier.width(12.dp).height(12.dp).background(color, shape = CircleShape)
                    )
                }
            }
            Spacer(Modifier.height(26.dp))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                IconButton(
                    onClick = { viewModel.stopTimer() },
                    modifier = Modifier
                        .size(56.dp)
                        .border(2.dp, activeColor, shape = RoundedCornerShape(12.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = activeColor
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.stop_fill),
                        contentDescription = "Pomodoro Stop",
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(Modifier.width(24.dp))
                IconButton(
                    onClick = { viewModel.toggleTimer() },
                    modifier = Modifier
                        .size(88.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .border(2.dp, activeColor, shape = RoundedCornerShape(12.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = activeColor
                    )
                ) {
                    Icon(
                        painter = painterResource(
                            if (viewModel.isRunning) R.drawable.pause_fill else R.drawable.play
                        ),
                        contentDescription = "Pomodoro Play/Pause",
                        modifier = Modifier.size(44.dp)
                    )
                }
                Spacer(Modifier.width(24.dp))
                IconButton(
                    onClick = { viewModel.skipSession() },
                    modifier = Modifier
                        .size(56.dp)
                        .border(2.dp, activeColor, shape = RoundedCornerShape(12.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = activeColor
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.skip_end),
                        contentDescription = "Pomodoro Skip",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            Spacer(Modifier.height(22.dp))
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(tasks.size) { index ->
                    val task = tasks[index]
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .background(color = if(task.isComplete) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { viewModel.toggleTaskCompletion(task.id) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Checkbox(
                                colors = CheckboxDefaults.colors(
                                    uncheckedColor = MaterialTheme.colorScheme.outline
                                ),
                                checked = task.isComplete,
                                onCheckedChange = { viewModel.toggleTaskCompletion(task.id) },
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                task.title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if(task.isComplete) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        IconButton(
                            onClick = {
                                showDeleteTaskDialog = true
                                tempDeleteTask = task
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Excluir tarefa",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}