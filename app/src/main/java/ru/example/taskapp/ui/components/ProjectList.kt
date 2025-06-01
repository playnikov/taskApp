package ru.example.taskapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import ru.example.taskapp.data.model.Project

@Composable
fun ProjectList(projects: List<Project>, onProjectClick: (Int) -> Unit) {
    LazyColumn {
        items(projects) { project ->
            ProjectItemClickable(project) {
                onProjectClick(it)
            }
        }
    }
}