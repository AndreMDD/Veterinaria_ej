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

    // --- Funciones de Autenticación (NUEVAS) ---

    suspend fun signup(request: SignUpRequest): Response<Unit> {
        return apiService.signup(request)
    }

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)
    }
}
