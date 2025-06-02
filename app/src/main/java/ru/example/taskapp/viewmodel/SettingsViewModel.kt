package ru.example.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.example.taskapp.data.repository.SettingsRepository

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}