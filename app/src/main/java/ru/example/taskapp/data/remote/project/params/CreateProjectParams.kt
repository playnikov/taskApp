package ru.example.taskapp.data.remote.project.params

data class CreateProjectParams(
    val title: String,
    val description: String?=null,
    val ownerId: Int
)
