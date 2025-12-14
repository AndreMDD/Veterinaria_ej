package com.uno.veterinaria.network

import models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz de Retrofit dedicada exclusivamente a los endpoints de la API de OpenWeather.
 */
interface OpenWeatherApiService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric", // Para obtener la temperatura en Celsius
        @Query("lang") lang: String = "es"      // Para obtener la descripción en español
    ): WeatherResponse
}
