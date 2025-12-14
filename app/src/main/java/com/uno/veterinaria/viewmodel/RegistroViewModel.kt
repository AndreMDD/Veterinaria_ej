package com.uno.veterinaria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uno.veterinaria.repository.CitasRepository
import kotlinx.coroutines.launch
import models.SignUpRequest

class RegistroViewModel : ViewModel() {

    private val repository = CitasRepository()

    // LiveData para comunicar el resultado del registro a la Activity.
    private val _registroResult = MutableLiveData<Result<Unit>>()
    val registroResult: LiveData<Result<Unit>> = _registroResult

    fun registrarNuevoUsuario(request: SignUpRequest) {
        viewModelScope.launch {
            try {
                val response = repository.signup(request)
                if (response.isSuccessful) {
                    // Si la API responde con éxito, se notifica a la Activity.
                    _registroResult.postValue(Result.success(Unit))
                } else {
                    // Si la API responde con un error (ej. usuario ya existe), se notifica el error.
                    val errorMsg = "Error ${response.code()}: ${response.message()}"
                    _registroResult.postValue(Result.failure(Exception(errorMsg)))
                }
            } catch (e: Exception) {
                // Si ocurre un error de red (ej. sin internet), se notifica.
                _registroResult.postValue(Result.failure(e))
            }
        }
    }
}
