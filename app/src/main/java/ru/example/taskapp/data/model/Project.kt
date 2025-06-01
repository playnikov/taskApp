package ru.example.taskapp.data.model

data class Project(
    val id: Int,
    val title: String,
    val description: String?=null,
    val ownerId: Int,
    val ownerName: String?=null,
    val createdAt: String
)
