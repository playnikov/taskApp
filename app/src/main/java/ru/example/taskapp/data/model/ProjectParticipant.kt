package ru.example.taskapp.data.model

data class ProjectParticipant(
    val id: Int,
    val projectId: Int,
    val userId: Int,
    val userName: String?=null,
    val userEmail: String?=null,
    val userRole: String?=null
)
