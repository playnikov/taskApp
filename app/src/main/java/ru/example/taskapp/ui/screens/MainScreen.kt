package ru.example.taskapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.example.taskapp.ui.components.BottomNavigationBar

@Composable
fun MainScreen(
) {
    val mainNavController = rememberNavController()
    LaunchedEffect("projects") {
        mainNavController.navigate("projects")
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(mainNavController) }
    ) { padding ->
        NavHost(
            navController = mainNavController,
            startDestination = "projects",
            modifier = Modifier.padding(padding)
        ) {
            composable("projects") {

            }
        }
    }
}