package com.uno.veterinaria.repository

import com.uno.veterinaria.network.OpenWeatherApiService
import com.uno.veterinaria.network.OpenWeatherRetrofitClient
import models.WeatherResponse

/**
 * Repositorio dedicado exclusivamente a obtener datos de la API de OpenWeather.
 */
class WeatherRepository {

    private val apiService: OpenWeatherApiService = OpenWeatherRetrofitClient.instance

    suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String): WeatherResponse {
        return apiService.getCurrentWeather(lat, lon, apiKey)
    }
}
