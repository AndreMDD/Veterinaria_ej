package models

import com.google.gson.annotations.SerializedName

/**
 * Este modelo de datos AHORA SÍ coincide con la respuesta JSON de tu API de Xano.
 * Se usan las anotaciones @SerializedName para mapear los nombres exactos del JSON (con mayúsculas)
 * a nombres de propiedades estándar en Kotlin (camelCase).
 */
data class HistorialCita(
    @SerializedName("id")
    val id: Int,

    @SerializedName("mascota_id")
    val mascotaId: Int,

    // Se mapea el campo "Fecha_Hora" que contiene un timestamp en milisegundos
    @SerializedName("Fecha_Hora")
    val fechaHoraTimestamp: Long,

    // Se mapea el campo "Motivo" (con mayúscula inicial)
    @SerializedName("Motivo")
    val motivo: String
)
