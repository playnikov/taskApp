package ru.example.taskapp.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.example.taskapp.data.model.User

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("auth/validate")
    suspend fun validateToken(@Header("Authorization") token: String): Response<Unit>
}

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(
    val data: AuthData?
) {
    data class AuthData(
        val authToken: String?,
        val user: User?
    )
}