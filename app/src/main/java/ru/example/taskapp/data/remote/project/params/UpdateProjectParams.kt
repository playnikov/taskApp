package ru.example.taskapp.data.remote.project.params

data class UpdateProjectParams(
    val id: Int,
    val title: String?=null,
    val description: String?=null,
    val ownerId: Int?=null
)
