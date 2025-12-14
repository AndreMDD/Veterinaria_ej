package models

import com.google.gson.annotations.SerializedName

/**
 * Modelos de datos para la respuesta de la API de OpenWeather.
 */

data class WeatherResponse(
    @SerializedName("weather")
    val weather: List<Weather>,

    @SerializedName("main")
    val main: Main,

    @SerializedName("name")
    val name: String
)

data class Weather(
    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
)

data class Main(
    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("humidity")
    val humidity: Int
)
