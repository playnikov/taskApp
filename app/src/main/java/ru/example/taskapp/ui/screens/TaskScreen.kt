package ru.example.taskapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.example.taskapp.data.model.Task
import ru.example.taskapp.ui.components.SearchBox
import ru.example.taskapp.ui.components.TaskItem
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors
import ru.example.taskapp.viewmodel.TaskViewModel

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = koinViewModel()
) {
    val selectedTask by viewModel.selectedTask.collectAsState()

    val newTasks by viewModel.newTasks.collectAsState()
    val inProgressTasks by viewModel.inProgressTasks.collectAsState()
    val completedTasks by viewModel.completedTasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

//    if (isLoading) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator()
//        }
//        return
//    }

    var query by remember { mutableStateOf("") }
    val colors = LocalExtendedColors.current

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

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Новые задачи",
                    style = MaterialTheme.typography.titleSmall,
                    color = colors.azure,
                    fontSize = 20.sp
                )

                TaskList(tasks = newTasks, onStatusChange = {})
            }

            item {
                Text(
                    text = "В работе",
                    style = MaterialTheme.typography.titleSmall,
                    color = colors.azure,
                    fontSize = 20.sp
                )

                TaskList(tasks = inProgressTasks, onStatusChange = {})
            }

            item {
                Text(
                    text = "Завершены",
                    style = MaterialTheme.typography.titleSmall,
                    color = colors.ming.copy(0.4f),
                    fontSize = 20.sp
                )

                TaskList(tasks = completedTasks, onStatusChange = {})
            }
        }
    }
}

@Composable
fun TaskList(tasks: List<Task>, onStatusChange: ((Int) -> Unit)?) {
    Column {
        tasks.forEach { task ->
            TaskItem(
                task = task
            )
        }
    }
}