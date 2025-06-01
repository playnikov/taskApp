package ru.example.taskapp.data.model

data class Task(
    val id: Int,
    val title: String,
    val description: String?=null,
    val category: String?=null,
    val projectId: Int,
    val assigneeId: Int,
    val assigneeName: String?=null,
    val developerId: Int,
    val developerName: String?=null,
    val status: String,
    val priority: String,
    val deadline: String,
    val createdAt: String,
    val updatedAt: String
)
