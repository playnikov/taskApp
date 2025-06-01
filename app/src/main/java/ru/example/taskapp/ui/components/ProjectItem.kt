package ru.example.taskapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.example.taskapp.data.model.Project
import ru.example.taskapp.ui.theme.TaskAppTheme
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors
import java.time.LocalDateTime

@Composable
fun ProjectItem(
    project: Project
) {
    val colors = LocalExtendedColors.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = project.title,
                style = MaterialTheme.typography.titleMedium,
                color = colors.ming,
                fontSize = 20.sp
            )

            Text(
                text = project.description ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = colors.ming.copy(0.4f),
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProjectItem() {
    TaskAppTheme {
        ProjectItem(
            Project(
                id = 1,
                title = "Проект 1",
                description = "Описание проекта 1",
                ownerId = 1,
                createdAt =  "01-01-2025"
            )
        )
    }
}