package ru.example.taskapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.example.taskapp.ui.components.NextButton
import ru.example.taskapp.ui.components.ProjectDetailDialog
import ru.example.taskapp.ui.components.ProjectList
import ru.example.taskapp.ui.components.SearchBox
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors
import ru.example.taskapp.viewmodel.ProjectViewModel

@Composable
fun ProjectScreen(
    viewModel: ProjectViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.projectsState.collectAsState()
    var query by remember { mutableStateOf("") }
    val userRole by viewModel.userRole.collectAsState()

    val colors = LocalExtendedColors.current

    val filteredProjects = when (state) {
        is ProjectViewModel.ProjectState.Success -> {
            val projects = (state as ProjectViewModel.ProjectState.Success).projects
            if (query.isNotBlank()) {
                projects.filter { it.title.contains(query, ignoreCase = true) }
            } else {
                projects
            }
        }
        else -> emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp)
    ) {
        SearchBox(
            query = query,
            onQueryChange = { query = it }
        )

        Spacer(modifier = Modifier.height(16.dp))


        when(state) {
            is ProjectViewModel.ProjectState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ProjectViewModel.ProjectState.Success -> {
                if (filteredProjects.isEmpty()) {
                    Text("No projects found.")
                } else {
                    ProjectList(
                        projects = filteredProjects,
                        onProjectClick = { projectId ->
                            when (userRole) {
                                "admin" ->navController.navigate("project_detail/${projectId}")
                                else -> {}
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (userRole == "admin") {
                    NextButton(
                        text = "Добавить проект",
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            is ProjectViewModel.ProjectState.Error -> {
                val message = (state as ProjectViewModel.ProjectState.Error).message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error loading projects",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadProjects() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}