package models

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para la petición POST al crear una nueva cita.
 * Los nombres de los campos (@SerializedName) deben coincidir con lo que espera tu API en Xano.
 * Se asumen nombres basados en la respuesta del GET, pero puedes ajustarlos si es necesario.
 */
data class AgendarCitaRequest(
    @SerializedName("nombre_mascota")
    val nombreMascota: String,

    @SerializedName("especie")
    val especie: String,

    @SerializedName("edad")
    val edad: Int,

    @SerializedName("sexo")
    val sexo: String,

    @SerializedName("chip")
    val chip: String?,

    @SerializedName("nombre_dueno")
    val nombreDueno: String,

    // Se enviará un timestamp, igual que en la respuesta del GET
    @SerializedName("Fecha_Hora")
    val fechaHoraTimestamp: Long,

    @SerializedName("Motivo")
    val motivo: String
)
