package com.uno.veterinaria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uno.veterinaria.repository.CitasRepository

/**
 * Esta clase es una "fábrica" que sabe cómo crear nuestro FichaClinicaViewModel.
 * Es necesaria porque el ViewModel ahora tiene un constructor con parámetros (el repositorio).
 */
class FichaClinicaViewModelFactory(private val repository: CitasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FichaClinicaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FichaClinicaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
