package com.uno.veterinaria.repository

import com.uno.veterinaria.network.AuthRetrofitClient
import com.uno.veterinaria.network.RetrofitClient
import models.AgendarCitaRequest
import models.HistorialCita
import models.LoginRequest
import models.LoginResponse
import models.SignUpRequest
import models.Ubicacion
import retrofit2.Response

/**
 * Repositorio que maneja toda la lógica de datos de la aplicación.
 * Ahora es capaz de comunicarse con dos fuentes de API diferentes.
 */
class CitasRepository {

    // Se crean dos instancias de ApiService, una para cada cliente de Retrofit.
    private val citasApiService = RetrofitClient.instance
    private val authApiService = AuthRetrofitClient.instance

    suspend fun getHistorialCitas(dueno: String? = null): List<HistorialCita> {
        // Usa el cliente de citas
        return citasApiService.getHistorialCitas(dueno)
    }

    suspend fun agendarCita(request: AgendarCitaRequest): Response<HistorialCita> {
        // Usa el cliente de citas
        return citasApiService.agendarCita(request)
    }

    suspend fun getUbicacion(): Ubicacion {
        // Usa el cliente de citas
        return citasApiService.getUbicacion()
    }

    // --- Funciones de Autenticación ---

    suspend fun signup(request: SignUpRequest): Response<LoginResponse> {
        // Usa el cliente de autenticación
        return authApiService.signup(request)
    }

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        // Usa el cliente de autenticación
        return authApiService.login(request)
    }
}
