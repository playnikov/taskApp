package ru.example.taskapp.data.remote.user

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import ru.example.taskapp.data.model.User

interface UserApiService {
    @GET("/users")
    suspend fun getUsers(@Header("Authorization") token: String): Response<UserResponse>
}

data class UserResponse(
    @SerializedName("data")
    val users: List<User>
)