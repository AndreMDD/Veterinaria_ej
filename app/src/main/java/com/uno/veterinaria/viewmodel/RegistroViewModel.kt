package com.uno.veterinaria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uno.veterinaria.repository.CitasRepository
import kotlinx.coroutines.launch
import models.LoginResponse
import models.SignUpRequest

class RegistroViewModel : ViewModel() {

    private val repository = CitasRepository()

    // CORRECCIÓN: El LiveData ahora notificará el LoginResponse para el auto-login.
    private val _registroResult = MutableLiveData<Result<LoginResponse>>()
    val registroResult: LiveData<Result<LoginResponse>> = _registroResult

    fun registrarNuevoUsuario(request: SignUpRequest) {
        viewModelScope.launch {
            try {
                val response = repository.signup(request)
                if (response.isSuccessful && response.body() != null) {
                    // Si la API responde con éxito, se envía el LoginResponse a la Activity.
                    _registroResult.postValue(Result.success(response.body()!!))
                } else {
                    // Si la API responde con un error, se notifica.
                    val errorMsg = "Error ${response.code()}: ${response.message()}"
                    _registroResult.postValue(Result.failure(Exception(errorMsg)))
                }
            } catch (e: Exception) {
                // Si hay un error de red, se notifica.
                _registroResult.postValue(Result.failure(e))
            }
        }
    }
}
