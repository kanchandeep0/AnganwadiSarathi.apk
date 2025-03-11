package com.saikia.amganwadisarathi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

sealed class Screen(
    val route: String,
    val icon: (@Composable () -> Unit)? = null,
    val label: String = ""
) {
    // Bottom navigation items
    object Home : Screen(
        route = "home",
        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
        label = "Home"
    )
    object Profile : Screen(
        route = "profile",
        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
        label = "Profile"
    )
    object Settings : Screen(
        route = "settings",
        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
        label = "Settings"
    )
    
    // Other screens without bottom nav
    object Login : Screen("login")
    object Attendance : Screen("attendance")
    object VerifyAttendance : Screen("verify_attendance")
    object WorkerRegistration : Screen("worker_registration")
    object WorkerAttendance : Screen("worker_attendance")
    object StudentAttendance : Screen("student_attendance")
    object AttendanceHistory : Screen("attendance_history")
    object Supplies : Screen("supplies")
    object Report : Screen("report")

    companion object {
        fun bottomNavItems() = listOf(Home, Profile, Settings)
    }
}
