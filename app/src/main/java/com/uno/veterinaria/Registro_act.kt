package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import com.uno.veterinaria.viewmodel.RegistroViewModel
import models.SignUpRequest

class Registro_act : AppCompatActivity() {

    private val viewModel: RegistroViewModel by viewModels()

    private lateinit var tilNombreCompleto: TextInputLayout
    private lateinit var tilCorreo: TextInputLayout
    private lateinit var tilTelefono: TextInputLayout
    private lateinit var tilContrasena: TextInputLayout
    private lateinit var tilConfirmarContrasena: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        initializeViews()
        setupListeners()
        observeViewModel()
    }

    private fun initializeViews() {
        tilNombreCompleto = findViewById(R.id.etNombreCompleto_layout)
        tilCorreo = findViewById(R.id.etCorreo_layout)
        tilTelefono = findViewById(R.id.etTelefono_layout)
        tilContrasena = findViewById(R.id.etContrasena_layout)
        tilConfirmarContrasena = findViewById(R.id.etConfirmarContrasena_layout)
    }

    private fun setupListeners() {
        findViewById<Button>(R.id.btnRegistrarse).setOnClickListener {
            if (validateFields()) {
                val request = SignUpRequest(
                    nombreCompleto = tilNombreCompleto.editText?.text.toString(),
                    correo = tilCorreo.editText?.text.toString(),
                    telefono = tilTelefono.editText?.text.toString(),
                    contrasena = tilContrasena.editText?.text.toString()
                )
                viewModel.registrarNuevoUsuario(request)
            }
        }

        findViewById<TextView>(R.id.tvIniciarSesion).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.registroResult.observe(this, Observer { result ->
            result.onSuccess {
                Toast.makeText(this, "Registro exitoso. Ahora puedes iniciar sesión.", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            result.onFailure { error ->
                val errorMessage = if (error.message?.contains("409") == true) {
                    "El correo electrónico ya está registrado."
                } else {
                    "Error en el registro: ${error.message}"
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (tilNombreCompleto.editText?.text.toString().trim().isEmpty()) {
            tilNombreCompleto.error = "El nombre no puede estar vacío"
            isValid = false
        } else {
            tilNombreCompleto.error = null
        }

        if (tilCorreo.editText?.text.toString().trim().isEmpty()) {
            tilCorreo.error = "El correo no puede estar vacío"
            isValid = false
        } else {
            tilCorreo.error = null
        }
        
        if (tilTelefono.editText?.text.toString().trim().isEmpty()) {
            tilTelefono.error = "El teléfono no puede estar vacío"
            isValid = false
        } else {
            tilTelefono.error = null
        }

        val contrasena = tilContrasena.editText?.text.toString()
        if (contrasena.isEmpty()) {
            tilContrasena.error = "La contraseña no puede estar vacía"
            isValid = false
        } else {
            tilContrasena.error = null
        }

        val confirmarContrasena = tilConfirmarContrasena.editText?.text.toString()
        if (confirmarContrasena != contrasena) {
            tilConfirmarContrasena.error = "Las contraseñas no coinciden"
            isValid = false
        } else {
            tilConfirmarContrasena.error = null
        }

        return isValid
    }
}
