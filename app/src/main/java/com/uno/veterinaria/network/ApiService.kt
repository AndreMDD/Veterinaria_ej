package com.uno.veterinaria.network

import models.HistorialCita
import models.Ubicacion
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // CORRECCIÓN: Se cambia el nombre para reflejar que no siempre trae todas las citas
    // Y se añade el parámetro @Query. Retrofit lo convertirá en "/historialcitas?nombre_dueno=juan"
    @GET("historialcitas")
    suspend fun getHistorialCitas(@Query("nombre_dueno") dueno: String? = null): List<HistorialCita>

    @GET("ubicacion")
    suspend fun getUbicacion(): Ubicacion
}
