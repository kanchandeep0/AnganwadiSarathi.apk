package com.saikia.amganwadisarathi

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import androidx.navigation.NavController
import com.saikia.amganwadisarathi.navigation.Screen
import com.saikia.amganwadisarathi.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                Screen.bottomNavItems().forEach { screen ->
                    NavigationBarItem(
                        icon = { screen.icon?.invoke() },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { HomeScreen(navController) }
                composable(Screen.Profile.route) { ProfileScreen(navController) }
                composable(Screen.Settings.route) { SettingsScreen(navController) }
                composable(Screen.Attendance.route) { AttendanceScreen(navController) }
                composable(Screen.VerifyAttendance.route) { VerifyAttendanceScreen(navController) }
                composable(Screen.WorkerRegistration.route) { WorkerRegistrationScreen(navController) }
                composable(Screen.WorkerAttendance.route) { WorkerAttendanceScreen(navController) }
                composable(Screen.StudentAttendance.route) { StudentAttendanceScreen(navController) }
                composable(Screen.AttendanceHistory.route) { AttendanceHistoryScreen(navController) }
                composable(Screen.Supplies.route) { SupplyScreen(navController) }
                composable(Screen.Report.route) { ReportScreen(navController) }
            }
        }
    }
}

@Composable
fun MainContent(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate(Screen.WorkerRegistration.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Register Worker"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Register Worker")
        }

        Button(
            onClick = { navController.navigate(Screen.Attendance.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Mark Attendance"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Mark Attendance")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DashboardCard(
            title = "Worker Registration",
            icon = Icons.Default.Person,
            onClick = { navController.navigate(Screen.WorkerRegistration.route) }
        )
        DashboardCard(
            title = "Mark Attendance",
            icon = Icons.Default.Add,
            onClick = { navController.navigate(Screen.Attendance.route) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerCard(
    worker: Worker,
    onMarkAttendance: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onMarkAttendance
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = worker.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = worker.jobRole,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onMarkAttendance) {
                Icon(Icons.Default.Person, contentDescription = "Mark Attendance")
            }
        }
    }
}
