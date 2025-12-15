package com.uno.veterinaria.network

import models.AgendarCitaRequest
import models.HistorialCita
import models.LoginRequest
import models.LoginResponse
import models.SignUpRequest
import models.Ubicacion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // --- Endpoints de Citas ---
    @GET("historialcitas")
    suspend fun getHistorialCitas(@Query("nombre_dueno") dueno: String? = null): List<HistorialCita>

    @POST("historialcitas")
    suspend fun agendarCita(@Body request: AgendarCitaRequest): Response<HistorialCita>

    // --- Endpoint de Ubicación ---
    @GET("ubicacion")
    suspend fun getUbicacion(): Ubicacion

    // --- Endpoints de Autenticación (CORRECCIÓN FINAL) ---

    @POST("auth/signup") // Se elimina la barra inicial para evitar el doble slash
    suspend fun signup(@Body request: SignUpRequest): Response<LoginResponse>

    @POST("auth/login") // Se elimina la barra inicial para evitar el doble slash
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
