package com.uno.veterinaria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uno.veterinaria.repository.CitasRepository
import kotlinx.coroutines.launch
import models.Ubicacion

class InicioViewModel : ViewModel() {

    private val repository = CitasRepository()

    private val _ubicacion = MutableLiveData<Ubicacion>()
    val ubicacion: LiveData<Ubicacion> = _ubicacion

    fun cargarUbicacion() {
        viewModelScope.launch {
            try {
                val result = repository.getUbicacion()
                _ubicacion.postValue(result)
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }
}
