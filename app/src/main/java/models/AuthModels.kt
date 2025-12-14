package models

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para la petición de inicio de sesión (login).
 * Contiene las credenciales que el usuario introduce.
 */
data class LoginRequest(
    @SerializedName("correo")
    val correo: String,

    @SerializedName("contrasena")
    val contrasena: String
)

/**
 * Modelo de datos para la respuesta exitosa del inicio de sesión.
 * Contiene el token de autenticación y el rol del usuario, que la API devuelve.
 */
data class LoginResponse(
    @SerializedName("authToken")
    val authToken: String,

    @SerializedName("rol")
    val rol: String
)
