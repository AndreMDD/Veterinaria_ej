package com.uno.veterinaria.repository

import com.uno.veterinaria.network.RetrofitClient
import models.AgendarCitaRequest
import models.HistorialCita
import models.LoginRequest
import models.LoginResponse
import models.SignUpRequest
import models.Ubicacion
import retrofit2.Response

class CitasRepository {

    private val apiService = RetrofitClient.instance

    suspend fun getHistorialCitas(dueno: String? = null): List<HistorialCita> {
        return apiService.getHistorialCitas(dueno)
    }

    suspend fun agendarCita(request: AgendarCitaRequest): Response<HistorialCita> {
        return apiService.agendarCita(request)
    }

    suspend fun getUbicacion(): Ubicacion {
        return apiService.getUbicacion()
    }

    // --- Funciones de Autenticación ---

    // CORRECCIÓN: La función ahora devuelve un LoginResponse para manejar el token de auto-login.
    suspend fun signup(request: SignUpRequest): Response<LoginResponse> {
        return apiService.signup(request)
    }

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)
    }
}
