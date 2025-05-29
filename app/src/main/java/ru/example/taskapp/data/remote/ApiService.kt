package ru.example.taskapp.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(
    val data: AuthData?
) {
    data class AuthData(
        val authToken: String?
    )
}