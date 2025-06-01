package ru.example.taskapp.data.model

data class Chat(
    val id: Int,
    val title: String,
    val isGroup: Boolean = false,
    val createdAt: String,
    val updatedAt: String
)
