package models

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para la petición de inicio de sesión (login).
 */
data class LoginRequest(
    @SerializedName("Correo")
    val correo: String,

    @SerializedName("Contrasena")
    val contrasena: String
)

/**
 * Modelo de datos para la respuesta exitosa del inicio de sesión.
 * REVERSIÓN: Se corrige @SerializedName("role") de vuelta a @SerializedName("rol") 
 * para que coincida con la respuesta real del backend.
 */
data class LoginResponse(
    @SerializedName("authToken")
    val authToken: String,

    @SerializedName("rol")
    val rol: String
)
