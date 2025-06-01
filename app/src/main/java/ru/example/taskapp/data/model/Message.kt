package ru.example.taskapp.data.model

data class Message(
    val id: Int,
    val chatId: Int,
    val senderId: Int,
    val content: String,
    val type: String,
    val isRead: Boolean = false,
    val createdAt: String
)
