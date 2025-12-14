package com.uno.veterinaria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uno.veterinaria.repository.CitasRepository
import kotlinx.coroutines.launch
import models.AgendarCitaRequest
import models.HistorialCita

class AgendarHoraViewModel : ViewModel() {

    private val repository = CitasRepository()

    private val _agendamientoResult = MutableLiveData<Result<HistorialCita>>()
    val agendamientoResult: LiveData<Result<HistorialCita>> = _agendamientoResult

    fun agendarNuevaCita(request: AgendarCitaRequest) {
        viewModelScope.launch {
            try {
                val response = repository.agendarCita(request)
                if (response.isSuccessful && response.body() != null) {
                    _agendamientoResult.postValue(Result.success(response.body()!!))
                } else {
                    _agendamientoResult.postValue(Result.failure(Exception("Error ${response.code()}: ${response.message()}")))
                }
            } catch (e: Exception) {
                _agendamientoResult.postValue(Result.failure(e))
            }
        }
    }
}
