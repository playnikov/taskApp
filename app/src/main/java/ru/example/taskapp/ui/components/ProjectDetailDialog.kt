package ru.example.taskapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import ru.example.taskapp.data.model.Project
import ru.example.taskapp.utils.formattedDate

@Composable
fun ProjectDetailDialog(project: Project, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = project.title)
        },
        text = {
            Column {
                Text(text = "Описание: ${project.description ?: "Нет описания"}")
                Text(text = "Менеджер: ${project.ownerName ?: ""}")
                Text(text = "Дата создания: ${formattedDate(project.createdAt)}")
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}