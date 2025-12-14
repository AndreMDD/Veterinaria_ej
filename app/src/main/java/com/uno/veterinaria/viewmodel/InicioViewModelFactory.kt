package com.uno.veterinaria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uno.veterinaria.repository.CitasRepository
import com.uno.veterinaria.repository.WeatherRepository

/**
 * Fábrica para crear instancias de InicioViewModel, proveyendo los repositorios necesarios.
 */
class InicioViewModelFactory(
    private val citasRepository: CitasRepository,
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InicioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InicioViewModel(citasRepository, weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
