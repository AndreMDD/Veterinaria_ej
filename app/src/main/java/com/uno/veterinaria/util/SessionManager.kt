package com.uno.veterinaria.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Clase que centraliza la gestión de la sesión del usuario (SharedPreferences).
 * Esto evita repetir código y facilita el mantenimiento.
 */
class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        const val AUTH_TOKEN = "auth_token"
        const val USER_ROLE = "user_role"
        const val USER_NAME = "user_name"
    }

    fun saveAuthToken(token: String) {
        prefs.edit().putString(AUTH_TOKEN, token).apply()
    }

    fun saveUserRole(role: String) {
        prefs.edit().putString(USER_ROLE, role).apply()
    }

    fun saveUserName(name: String) {
        prefs.edit().putString(USER_NAME, name).apply()
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
