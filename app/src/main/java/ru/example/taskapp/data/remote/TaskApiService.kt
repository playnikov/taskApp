package ru.example.taskapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import ru.example.taskapp.data.model.Task

interface TaskApiService {
    @GET("/task/myTask")
    suspend fun getMyTasks(
        @Header("Authorization") token: String
    ): Response<TasksResponse>
}

data class TasksResponse(
    @SerializedName("data")
    val tasks: List<Task>
)