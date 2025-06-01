package ru.example.taskapp.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formattedDate(date: String): String {
    return try {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val date = LocalDateTime.parse(date, formatter)
        DateTimeFormatter
            .ofPattern("dd MMM yyyy, HH:mm")
            .format(date)
    } catch (e: Exception) {
        date
    }
}