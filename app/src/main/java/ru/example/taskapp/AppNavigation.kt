package ru.example.taskapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.example.taskapp.ui.screens.LoginScreen
import ru.example.taskapp.ui.screens.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }

        composable(
            route = "main"
        ) {
            MainScreen(navController = navController)
        }
    }
}