package ru.example.taskapp.data.model

data class User(
    val id: Int,
    val username: String,
    val lastName: String,
    val firstName: String,
    val middleName: String? = null,
    val email: String,
    val type: String,
    val createdAt: String
)