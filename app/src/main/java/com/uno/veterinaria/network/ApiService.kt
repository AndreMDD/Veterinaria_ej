package com.uno.veterinaria.network

import models.HistorialCita
import models.Ubicacion
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("citas")
    suspend fun getCitas(): List<HistorialCita>

    @GET("historialcitas")
    suspend fun getHistorialCitas(@Query("nombre_dueno") dueno: String? = null): List<HistorialCita>

    @GET("ubicacion")
    suspend fun getUbicacion(): Ubicacion
}
