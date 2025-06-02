package ru.example.taskapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import ru.example.taskapp.R
import ru.example.taskapp.data.model.Task
import ru.example.taskapp.ui.theme.TaskAppTheme
import ru.example.taskapp.ui.theme.colors.AppExtendedColors
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors
import ru.example.taskapp.utils.formattedDate
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.text.category

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    val colors = LocalExtendedColors.current
    val deadlineDateTime = convertToUserTimeZone(LocalDateTime.parse(task.deadline))
    val formattedDate = formatDate(deadlineDateTime)

    val timeLeft = calculateTimeLeft(deadlineDateTime)
    Card(
        modifier = modifier
            .fillMaxWidth(),
        border = BorderStroke(
            width = 1.dp,
            color = colors.blue.copy(0.4f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = getStatusColor(task.status.toTaskStatus()!!, colors)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar_today),
                        contentDescription = "Date",
                        tint = colors.ming,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(Modifier.width(7.dp))

                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.ming
                    )

                    Spacer(Modifier.width(6.dp))

                    if (task.category != null && task.category != "") {
                        Text(
                            text = task.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.ming,
                            modifier = Modifier
                                .background(
                                    color = colors.lightSkyBlue,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )

                        Spacer(Modifier.width(6.dp))
                    }

                    Icon(
                        painter = painterResource(R.drawable.ic_fire),
                        contentDescription = "Приоритет",
                        tint = getPriorityColor(task.priority.toTaskPriority()!!, colors),
                        modifier = Modifier.size(18.dp)
                    )

                }

                if (task.status.toTaskStatus() != TaskStatus.COMPLETED) {
                    Text(
                        text = timeLeft,
                        color = colors.ming,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.ming,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = task.assigneeName.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.ming
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Назначено",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(horizontal = 4.dp)
                )

                Text(
                    text = task.developerName.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.ming
                )
            }
        }
    }
}

private fun getStatusColor(status: TaskStatus, colors: AppExtendedColors): Color {
    return when(status) {
        TaskStatus.NEW -> colors.lightBlueWhite
        TaskStatus.COMPLETED -> colors.lightGreenWhite.copy(0.4f)
        TaskStatus.OVERDUE -> colors.vibrantRed
        TaskStatus.IN_PROGRESS -> colors.lightGreenWhite
    }
}

private fun getPriorityColor(priority: TaskPriority, colors: AppExtendedColors): Color {
    return when (priority) {
        TaskPriority.LOW -> colors.ming
        TaskPriority.MEDIUM -> colors.vibrantOrange
        TaskPriority.HIGH -> colors.vibrantRed
    }
}

private fun convertToUserTimeZone(utcDateTime: LocalDateTime): LocalDateTime {
    return utcDateTime.atZone(ZoneOffset.UTC)
        .withZoneSameInstant(ZoneId.systemDefault())
        .toLocalDateTime()
}

private fun formatDate(localDateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("d MMM HH:mm")
        .withLocale(Locale("ru"))
    return localDateTime.format(formatter)
}

private fun calculateTimeLeft(deadline: LocalDateTime): String {
    val now = LocalDateTime.now()
    if (now.isAfter(deadline)) return "Просрочено"

    val duration = Duration.between(now, deadline)

    return when {
        duration.toDays() > 0 -> {
            val days = duration.toDays()
            val hours = duration.minusDays(days).toHours()
            "$days д $hours ч"
        }
        duration.toHours() > 0 -> {
            val hours = duration.toHours()
            val minutes = duration.minusHours(hours).toMinutes()
            "$hours ч $minutes м"
        }
        duration.toMinutes() > 0 -> {
            val minutes = duration.toMinutes()
            val seconds = duration.minusMinutes(minutes).seconds
            "$minutes м $seconds с"
        }
        else -> "${duration.seconds} с"
    }
}

enum class TaskStatus(val displayName: String) {
    NEW("new"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    OVERDUE("overdue")
}

enum class TaskPriority(val displayName: String) {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high")
}

private fun String.toTaskStatus(): TaskStatus? {
    return enumValues<TaskStatus>().firstOrNull {
        it.name.equals(this, ignoreCase = true) ||
                it.displayName.equals(this, ignoreCase = true)
    }
}

private fun String.toTaskPriority(): TaskPriority? {
    return enumValues<TaskPriority>().firstOrNull {
        it.name.equals(this, ignoreCase = true) ||
                it.displayName.equals(this, ignoreCase = true)
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewNewTaskListItem() {
    TaskAppTheme {
        TaskItem(
            Task(
                id = 2,
                title = "API авторизации",
                description = "Реализовать endpoint для входа",
                category = "Бэкенд",
                projectId = 1,
                assigneeId = 2,
                assigneeName = "Петрова Ольга Игоревна",
                developerId = 5,
                developerName = "Никитина Елена Владимировна",
//                deadline = LocalDateTime.now().plusDays(2).plusHours(3),
                deadline = LocalDateTime.of(2025, 5, 25, 18, 0).toString(),
                status = TaskStatus.COMPLETED.toString(),
                priority = TaskPriority.LOW.toString(),
                createdAt = LocalDateTime.of(2025, 5, 25, 18, 0).toString(),
                updatedAt = LocalDateTime.of(2025, 5, 25, 18, 0).toString(),
            )
        )
    }
}


