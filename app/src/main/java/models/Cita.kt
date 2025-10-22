package models

data class Cita(
    val id: Int,
    val nombreMascota: String,
    val sexoMascota: String,
    val chipMascota: String,
    val nombreDueno: String,
    val fecha: String,
    val hora: String,
    val motivo: String
)