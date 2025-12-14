package com.uno.veterinaria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uno.veterinaria.repository.CitasRepository
import kotlinx.coroutines.launch
import models.HistorialCita

// CORRECCIÓN: El repositorio ahora se recibe en el constructor (Inyección de Dependencias)
class FichaClinicaViewModel(private val repository: CitasRepository) : ViewModel() {

    private val _citas = MutableLiveData<List<HistorialCita>>()
    val citas: LiveData<List<HistorialCita>> = _citas

    fun cargarCitas(dueno: String) {
        viewModelScope.launch {
            try {
                val citasDelDueno = repository.getHistorialCitas(dueno)
                _citas.postValue(citasDelDueno)

            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }
}
