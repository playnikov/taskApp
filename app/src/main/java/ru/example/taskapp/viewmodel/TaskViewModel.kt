package ru.example.taskapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.example.taskapp.data.model.Task
import ru.example.taskapp.data.repository.TaskRepository
import ru.example.taskapp.viewmodel.ProjectViewModel.ProjectState

class TaskViewModel (
    private val repository: TaskRepository
) : ViewModel() {

    private val _newTasks = MutableStateFlow<List<Task>>(emptyList())
    val newTasks: StateFlow<List<Task>> = _newTasks

    private val _inProgressTasks = MutableStateFlow<List<Task>>(emptyList())
    val inProgressTasks: StateFlow<List<Task>> = _inProgressTasks

    private val _completedTasks = MutableStateFlow<List<Task>>(emptyList())
    val completedTasks: StateFlow<List<Task>> = _completedTasks

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    var timeLeft by mutableStateOf("")
        private set

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val allTasks = repository.getMyTasks().getOrThrow()

                _newTasks.value = allTasks.filter { it.status == "new" }
                _inProgressTasks.value = allTasks.filter { it.status == "in_progress" }
                _completedTasks.value = allTasks.filter { it.status == "completed" }
            } catch (e: Exception) {
                _error.value = "Failed to load tasks: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    fun clearSelectedTask() {
        _selectedTask.value = null
    }
}