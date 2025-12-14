package models

import com.google.gson.annotations.SerializedName

/**
 * CORRECCIÓN: Modelo de datos para la petición de registro (signup).
 * Se actualizan las anotaciones @SerializedName para que coincidan con los nombres
 * exactos que espera la API de Xano.
 */
data class SignUpRequest(

    @SerializedName("Nombre_completo")
    val nombreCompleto: String,

    @SerializedName("Correo")
    val correo: String,

    @SerializedName("Telefono")
    val telefono: String,

    @SerializedName("Contrasena")
    val contrasena: String
)
