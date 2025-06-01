package ru.example.taskapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.example.taskapp.R
import ru.example.taskapp.ui.components.BackButton
import ru.example.taskapp.ui.components.NextButton
import ru.example.taskapp.ui.components.TextField
import ru.example.taskapp.ui.components.UserDropdown
import ru.example.taskapp.ui.theme.TaskAppTheme
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors
import ru.example.taskapp.viewmodel.ProjectViewModel

@Composable
fun ProjectDetailScreen(
    projectId: Int,
    viewModel: ProjectViewModel = koinViewModel(),
    navController: NavController
) {
    val colors = LocalExtendedColors.current
    val project by viewModel.project.collectAsState()
    val projectName by viewModel.projectName.collectAsState()
    val projectDescription by viewModel.projectDescription.collectAsState()
    val projectOwner by viewModel.projectOwner.collectAsState()
    val managers by viewModel.managers.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()

    LaunchedEffect(key1 = projectId) {
        viewModel.loadProject(projectId)
        viewModel.loadManagers()
    }

    val projectLocal = project

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = projectName,
                onValueChange = { viewModel.onProjectNameChanged(it) },
                placeholder = "Название проекта"
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = projectDescription,
                onValueChange = {  viewModel.onProjectDescriptionChanged(it) },
                placeholder = "Описание проекта"
            )

            Spacer(modifier = Modifier.height(16.dp))

            UserDropdown(
                users = managers,
                selectedUser = projectOwner,
                onUserSelected = { viewModel.onProjectOwnerSelected(it) }
            )
        }

        if (!isSaving) {
            NextButton(
                text = "Сохранить изменения",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        BackButton(
            text = "Назад",
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProjectDetailScreen() {
    TaskAppTheme {
     ProjectDetailScreen(
         projectId = 1,
         navController = rememberNavController()
     )
    }
}