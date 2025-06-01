package ru.example.taskapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.example.taskapp.data.model.Project
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors

@Composable
fun ProjectItemClickable(project: Project, onClick: (Int) -> Unit) {
    val colors = LocalExtendedColors.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(project.id) },
        colors = CardDefaults.cardColors(
            containerColor = colors.lightBlueWhite
        )
    ) {
        ProjectItem(project)
    }
}