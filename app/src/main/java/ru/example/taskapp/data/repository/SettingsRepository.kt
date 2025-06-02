package ru.example.taskapp.data.repository

import ru.example.taskapp.data.AppPreferences

class SettingsRepository(
    private val authRepository: AuthRepository,
    private val appPreferences: AppPreferences
) {
    fun getData(): String {
        return appPreferences.getUserRole().toString()
    }

    suspend fun logout() {
        authRepository.logout()
    }
}