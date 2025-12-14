package com.uno.veterinaria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uno.veterinaria.repository.CitasRepository
import com.uno.veterinaria.repository.WeatherRepository
import kotlinx.coroutines.launch
import models.Ubicacion
import models.WeatherResponse

/**
 * ViewModel para la pantalla de Inicio.
 * Ahora maneja la lógica para obtener tanto la ubicación de la veterinaria como el clima actual.
 * Se inyectan las dependencias de los repositorios para que sea testeable.
 */
class InicioViewModel(
    private val citasRepository: CitasRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    // --- LiveData para la Ubicación ---
    private val _ubicacion = MutableLiveData<Ubicacion>()
    val ubicacion: LiveData<Ubicacion> = _ubicacion

    // --- LiveData para el Clima ---
    private val _weather = MutableLiveData<Result<WeatherResponse>>()
    val weather: LiveData<Result<WeatherResponse>> = _weather

    fun cargarUbicacion() {
        viewModelScope.launch {
            try {
                val result = citasRepository.getUbicacion()
                _ubicacion.postValue(result)
            } catch (e: Exception) {
                // Manejar error de ubicación
            }
        }
    }

    fun cargarClima(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                val result = weatherRepository.getCurrentWeather(lat, lon, apiKey)
                _weather.postValue(Result.success(result))
            } catch (e: Exception) {
                _weather.postValue(Result.failure(e))
            }
        }
    }
}
