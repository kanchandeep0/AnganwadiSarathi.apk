package com.saikia.amganwadisarathi.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saikia.amganwadisarathi.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Anganwadi Sarathi",
            style = MaterialTheme.typography.headlineMedium
        )
        
        // Main actions
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Main Actions",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Button(
                    onClick = { navController.navigate(Screen.WorkerRegistration.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PersonAdd, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Register Worker")
                }
                
                Button(
                    onClick = { navController.navigate(Screen.Attendance.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Attendance")
                }
            }
        }
        
        // Additional features
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Additional Features",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Button(
                    onClick = { navController.navigate(Screen.Supplies.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Inventory, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Supplies")
                }
                
                Button(
                    onClick = { navController.navigate(Screen.Report.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Description, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Reports")
                }
            }
        }
    }
}

@Composable
private fun IconButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledTonalIconButton(
            onClick = onClick,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
