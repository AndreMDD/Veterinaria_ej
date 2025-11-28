package com.uno.veterinaria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uno.veterinaria.repository.CitasRepository
import kotlinx.coroutines.launch
import models.HistorialCita

class CitasAgendadasViewModel : ViewModel() {

    private val repository = CitasRepository()

    private val _citas = MutableLiveData<List<HistorialCita>>()
    val citas: LiveData<List<HistorialCita>> = _citas

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarCitasAgendadas() {
        viewModelScope.launch {
            try {
                val citasAgendadas = repository.getHistorialCitas()
                _citas.postValue(citasAgendadas)
            } catch (e: Exception) {
                _error.postValue("Error al cargar las citas: ${e.message}")
            }
        }
    }
}
