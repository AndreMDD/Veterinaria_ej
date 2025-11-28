package com.uno.veterinaria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uno.veterinaria.repository.CitasRepository
import kotlinx.coroutines.launch
import models.HistorialCita

class FichaClinicaViewModel : ViewModel() {

    private val repository = CitasRepository()

    private val _citas = MutableLiveData<List<HistorialCita>>()
    val citas: LiveData<List<HistorialCita>> = _citas

    fun cargarCitas(dueno: String) {
        viewModelScope.launch {
            try {
                // CORRECCIÓN: Ahora le pasamos el 'dueno' directamente a la llamada
                // del repositorio, que a su vez se lo pasará a la API.
                // Ya no es necesario filtrar la lista en la app.
                val citasDelDueno = repository.getHistorialCitas(dueno)
                _citas.postValue(citasDelDueno)

            } catch (e: Exception) {
                // Manejar el error, por ejemplo, posteando un estado de error a la UI
            }
        }
    }
}
