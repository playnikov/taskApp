package ru.example.taskapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "task_app_prefs")

class AppPreferences(
    private val context: Context
) {
    private val authTokenKey = stringPreferencesKey("auth_token")
    private val userRoleKey = stringPreferencesKey("user_role")

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[authTokenKey] = token
        }
    }

    fun getAuthToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[authTokenKey]
        }
    }

    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(authTokenKey)
        }
    }

    suspend fun saveRole(role: String) {
        context.dataStore.edit { preferences ->
            preferences[userRoleKey] = role
        }
    }

    fun getUserRole(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[userRoleKey]
        }
    }

    suspend fun clearUserRole() {
        context.dataStore.edit { preferences ->
            preferences.remove(userRoleKey)
        }
    }
}