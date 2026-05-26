package com.ikaroorg.pomodoro_app.ui.screen

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikaroorg.pomodoro_app.R
import com.ikaroorg.pomodoro_app.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val dots = listOf(1, 2, 3, 4)
    var isPomodoroPlay by remember { mutableStateOf(false) }
    val tasks = remember {
        mutableStateListOf<Task>(
            Task(
                id = "1",
                title = "Estudar Flutter",
                description = "Estudar para a prova de matematica"
            ),
            Task(
                id = "2",
                title = "Criar tela de pomodoro",
                description = "Trabalhar na empresa"
            ),
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
                        onClick = {},
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
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                TextButton(
                    onClick = {},
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                ) {
                    Text(
                        text = "Foco",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(Modifier.width(12.dp))
                TextButton(
                    onClick = {},
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(100))
                ) {
                    Text(
                        text = "Pausa Curta",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(Modifier.width(12.dp))
                TextButton(
                    onClick = {},
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(100))
                ) {
                    Text(
                        text = "Pausa Longa",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            Spacer(Modifier.height(26.dp))
            Column(
                Modifier.width(280.dp)
                    .height(280.dp)
                    .border(8.dp, MaterialTheme.colorScheme.primary, CircleShape),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "25:00",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.height(12.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(dots.size) { index ->
                    val color = if (index == 0) {
                        MaterialTheme.colorScheme.primary
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
                    onClick = { isPomodoroPlay = false },
                    modifier = Modifier
                        .size(56.dp)
                        .border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
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
                    onClick = { isPomodoroPlay = !isPomodoroPlay },
                    modifier = Modifier
                        .size(88.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        painter = painterResource(
                            if (isPomodoroPlay) R.drawable.pause_fill else R.drawable.play
                        ),
                        contentDescription = "Pomodoro Play/Pause",
                        modifier = Modifier.size(44.dp)
                    )
                }
                Spacer(Modifier.width(24.dp))
                IconButton(
                    onClick = {  },
                    modifier = Modifier
                        .size(56.dp)
                        .border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
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
                            .padding(16.dp)
                            .clickable {tasks[index] = task.copy(isComplete = !task.isComplete)},
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        Checkbox(
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colorScheme.outline
                            ),
                            checked = task.isComplete,
                            onCheckedChange = { tasks[index] = task.copy(isComplete = it) },
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
                }
            }
        }
    }
}
