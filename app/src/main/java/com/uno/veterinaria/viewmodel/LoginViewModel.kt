package com.uno.veterinaria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uno.veterinaria.repository.CitasRepository
import kotlinx.coroutines.launch
import models.LoginRequest
import models.LoginResponse

class LoginViewModel : ViewModel() {

    private val repository = CitasRepository()

    // LiveData para comunicar el resultado del login (éxito o fracaso) a la Activity
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun iniciarSesion(request: LoginRequest) {
        viewModelScope.launch {
            try {
                val response = repository.login(request)
                if (response.isSuccessful && response.body() != null) {
                    // Si la API devuelve un token, el login es exitoso
                    _loginResult.postValue(Result.success(response.body()!!))
                } else {
                    // Si la API devuelve un error (ej. 401 Unauthorized), se notifica el fracaso
                    val errorMsg = "Error ${response.code()}: Credenciales inválidas"
                    _loginResult.postValue(Result.failure(Exception(errorMsg)))
                }
            } catch (e: Exception) {
                // Si hay un error de red, se notifica
                _loginResult.postValue(Result.failure(e))
            }
        }
    }
}
