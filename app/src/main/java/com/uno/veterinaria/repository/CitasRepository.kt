package com.uno.veterinaria.repository

import com.uno.veterinaria.network.RetrofitClient
import models.HistorialCita
import models.Ubicacion

class CitasRepository {

    private val apiService = RetrofitClient.instance

    // CORRECCIÓN: La función ahora acepta un parámetro 'dueno' opcional
    // para poder pasarlo a la llamada de la API.
    suspend fun getHistorialCitas(dueno: String? = null): List<HistorialCita> {
        return apiService.getHistorialCitas(dueno)
    }

    suspend fun getUbicacion(): Ubicacion {
        return apiService.getUbicacion()
    }
}
