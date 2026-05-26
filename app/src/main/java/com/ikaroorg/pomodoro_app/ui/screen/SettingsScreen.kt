package com.ikaroorg.pomodoro_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ikaroorg.pomodoro_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController
) {
    var focusTime by remember { mutableIntStateOf(25) }
    var shortPause by remember { mutableIntStateOf(5) }
    var longPause by remember { mutableIntStateOf(15) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Configurações",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(16.dp))
             Column (
                 Modifier.fillMaxWidth()
                     .padding(12.dp, 8.dp),
                 horizontalAlignment = Alignment.Start,
             ){
                 Text(
                     "DURAÇÃO (MINUTOS)",
                     style = MaterialTheme.typography.labelLarge,
                     color = MaterialTheme.colorScheme.onBackground
                 )
                 Spacer(Modifier.height(6.dp))
                 Row(
                     modifier = Modifier.fillMaxWidth()
                         .background(MaterialTheme.colorScheme.surface)
                         .padding(12.dp, 12.dp),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically
                 ){
                    Text(
                        "Foco",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                     Row(
                         verticalAlignment = Alignment.CenterVertically
                     ){
                         IconButton (
                             onClick = {
                                 focusTime = focusTime - 5
                             },
                             enabled = focusTime > 5,
                             colors = IconButtonDefaults.iconButtonColors(
                                 containerColor = MaterialTheme.colorScheme.surface,
                                 contentColor = MaterialTheme.colorScheme.onSurface,
                             ),
                             modifier = Modifier.border(
                                 width = 1.dp,
                                 color = MaterialTheme.colorScheme.outline,
                                 shape = MaterialTheme.shapes.small
                             )
                                 .background(
                                     if(focusTime > 5) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant,
                                     shape = MaterialTheme.shapes.small
                                 )
                         ) {
                             Icon(
                                 painter = painterResource(R.drawable.less),
                                 contentDescription = "Diminuir tempo de foco",
                                 tint = MaterialTheme.colorScheme.onSurface,
                                 modifier = Modifier.size(12.dp)
                             )
                         }
                         Spacer(Modifier.width(16.dp))
                         Text(
                             focusTime.toString(),
                             style = MaterialTheme.typography.titleLarge,
                             color = MaterialTheme.colorScheme.primary,
                             fontWeight = FontWeight.Bold,
                             modifier = Modifier.width(40.dp),
                             textAlign = TextAlign.Center
                         )
                         Spacer(Modifier.width(16.dp))
                         IconButton (
                             onClick = {
                                 focusTime = focusTime + 5
                             },
                             colors = IconButtonDefaults.iconButtonColors(
                                 containerColor = MaterialTheme.colorScheme.surface,
                                 contentColor = MaterialTheme.colorScheme.onSurface
                             ),
                             modifier = Modifier.border(
                                 width = 1.dp,
                                 color = MaterialTheme.colorScheme.outline,
                                 shape = MaterialTheme.shapes.small
                             )
                         ) {
                             Icon(
                                 painter = painterResource(R.drawable.add),
                                 contentDescription = "Aumentar tempo de foco",
                                 tint = MaterialTheme.colorScheme.onSurface,
                                 modifier = Modifier.size(12.dp)
                             )
                         }
                     }
                 }
                 Spacer(Modifier.height(3.dp))
                 Row(
                     modifier = Modifier.fillMaxWidth()
                         .background(MaterialTheme.colorScheme.surface)
                         .padding(12.dp, 12.dp),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically
                 ){
                     Text(
                         "Pausa Curta",
                         style = MaterialTheme.typography.bodyLarge,
                         color = MaterialTheme.colorScheme.onSurface,
                         fontWeight = FontWeight.Bold
                     )
                     Row(
                         verticalAlignment = Alignment.CenterVertically
                     ){
                         IconButton (
                             onClick = {
                                 shortPause = shortPause - 5
                             },
                             enabled = shortPause > 5,
                             colors = IconButtonDefaults.iconButtonColors(
                                 containerColor = MaterialTheme.colorScheme.surface,
                                 contentColor = MaterialTheme.colorScheme.onSurface,
                             ),
                             modifier = Modifier.border(
                                 width = 1.dp,
                                 color = MaterialTheme.colorScheme.outline,
                                 shape = MaterialTheme.shapes.small
                             )
                                 .background(
                                     if(shortPause > 5) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant,
                                     shape = MaterialTheme.shapes.small
                                 )
                         ) {
                             Icon(
                                 painter = painterResource(R.drawable.less),
                                 contentDescription = "Diminuir tempo de pausa curta",
                                 tint = MaterialTheme.colorScheme.onSurface,
                                 modifier = Modifier.size(12.dp)
                             )
                         }
                         Spacer(Modifier.width(16.dp))
                         Text(
                             shortPause.toString(),
                             style = MaterialTheme.typography.titleLarge,
                             color = MaterialTheme.colorScheme.primary,
                             fontWeight = FontWeight.Bold,
                             modifier = Modifier.width(40.dp),
                             textAlign = TextAlign.Center
                         )
                         Spacer(Modifier.width(16.dp))
                         IconButton (
                             onClick = {
                                 shortPause = shortPause + 5
                             },
                             colors = IconButtonDefaults.iconButtonColors(
                                 containerColor = MaterialTheme.colorScheme.surface,
                                 contentColor = MaterialTheme.colorScheme.onSurface
                             ),
                             modifier = Modifier.border(
                                 width = 1.dp,
                                 color = MaterialTheme.colorScheme.outline,
                                 shape = MaterialTheme.shapes.small
                             )
                         ) {
                             Icon(
                                 painter = painterResource(R.drawable.add),
                                 contentDescription = "Aumentar tempo de pausa curta",
                                 tint = MaterialTheme.colorScheme.onSurface,
                                 modifier = Modifier.size(12.dp)
                             )
                         }
                     }
                 }
                 Spacer(Modifier.height(3.dp))
                 Row(
                     modifier = Modifier.fillMaxWidth()
                         .background(MaterialTheme.colorScheme.surface,
                             shape = RoundedCornerShape(
                                 bottomStart = 12.dp,
                                 bottomEnd = 12.dp
                             )
                         )
                         .padding(12.dp, 12.dp),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically
                 ){
                     Text(
                         "Pausa Longa",
                         style = MaterialTheme.typography.bodyLarge,
                         color = MaterialTheme.colorScheme.onSurface,
                         fontWeight = FontWeight.Bold
                     )
                     Row(
                         verticalAlignment = Alignment.CenterVertically
                     ){
                         IconButton (
                             onClick = {
                                 longPause = longPause - 5
                             },
                             enabled = longPause > 5,
                             colors = IconButtonDefaults.iconButtonColors(
                                 containerColor = MaterialTheme.colorScheme.surface,
                                 contentColor = MaterialTheme.colorScheme.onSurface,
                             ),
                             modifier = Modifier.border(
                                 width = 1.dp,
                                 color = MaterialTheme.colorScheme.outline,
                                 shape = MaterialTheme.shapes.small
                             )
                                 .background(
                                     if(longPause > 5) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant,
                                     shape = MaterialTheme.shapes.small
                                 )
                         ) {
                             Icon(
                                 painter = painterResource(R.drawable.less),
                                 contentDescription = "Diminuir tempo de pausa longa",
                                 tint = MaterialTheme.colorScheme.onSurface,
                                 modifier = Modifier.size(12.dp)
                             )
                         }
                         Spacer(Modifier.width(16.dp))
                         Text(
                             longPause.toString(),
                             style = MaterialTheme.typography.titleLarge,
                             color = MaterialTheme.colorScheme.primary,
                             fontWeight = FontWeight.Bold,
                             modifier = Modifier.width(40.dp),
                             textAlign = TextAlign.Center
                         )
                         Spacer(Modifier.width(16.dp))
                         IconButton (
                             onClick = {
                                 longPause = longPause + 5
                             },
                             colors = IconButtonDefaults.iconButtonColors(
                                 containerColor = MaterialTheme.colorScheme.surface,
                                 contentColor = MaterialTheme.colorScheme.onSurface
                             ),
                             modifier = Modifier.border(
                                 width = 1.dp,
                                 color = MaterialTheme.colorScheme.outline,
                                 shape = MaterialTheme.shapes.small
                             )
                         ) {
                             Icon(
                                 painter = painterResource(R.drawable.add),
                                 contentDescription = "Aumentar tempo de pausa logna",
                                 tint = MaterialTheme.colorScheme.onSurface,
                                 modifier = Modifier.size(12.dp)
                             )
                         }
                     }
                 }
             }
        }
    }
}