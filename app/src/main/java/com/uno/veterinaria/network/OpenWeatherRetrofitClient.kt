package com.uno.veterinaria.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente de Retrofit dedicado exclusivamente para la API de OpenWeather.
 */
object OpenWeatherRetrofitClient {

    private const val BASE_URL = "https://api.openweathermap.org/"

    val instance: OpenWeatherApiService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        retrofit.create(OpenWeatherApiService::class.java)
    }
}
