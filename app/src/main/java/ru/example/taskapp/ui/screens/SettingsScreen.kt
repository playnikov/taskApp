package ru.example.taskapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.example.taskapp.ui.components.NextButton
import ru.example.taskapp.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    navController: NavController
) {
    Column {
        NextButton(
            text = "Выйти",
            onClick = {
                viewModel.logout()
                navController.navigate("login") { popUpTo("login") { inclusive = true } }
            }
        )
    }
}