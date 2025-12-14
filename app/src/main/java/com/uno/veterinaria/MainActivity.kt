package com.uno.veterinaria

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.uno.veterinaria.viewmodel.LoginViewModel
import models.LoginRequest

class MainActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    // CORRECCIÓN: Se declara la variable a nivel de clase para que sea accesible en todos los métodos.
    private lateinit var etCorreo: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Se inicializan las vistas
        etCorreo = findViewById<TextInputEditText>(R.id.etCorreo)
        val etContrasena = findViewById<TextInputEditText>(R.id.etContrasena)
        val btnIniciarSesion = findViewById<Button>(R.id.btnLogin)
        val tvRegistrarse = findViewById<TextView>(R.id.tvRegister)

        btnIniciarSesion.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (correo.isNotEmpty() && contrasena.isNotEmpty()) {
                val request = LoginRequest(correo, contrasena)
                viewModel.iniciarSesion(request)
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegistrarse.setOnClickListener {
            val intent = Intent(this, Registro_act::class.java)
            startActivity(intent)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess { loginResponse ->
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                // Guardar el token y el rol en SharedPreferences
                val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("auth_token", loginResponse.authToken)
                    putString("user_role", loginResponse.rol)
                    // Ahora sí puede acceder a etCorreo porque es una propiedad de la clase.
                    putString("user_name", etCorreo.text.toString().trim())
                    apply()
                }

                // Navegar a la pantalla correspondiente según el rol
                if (loginResponse.rol.equals("admin", ignoreCase = true)) {
                    val intent = Intent(this, InicioAdmin_act::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, Inicio_act::class.java)
                    startActivity(intent)
                }
                finish()
            }
            result.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
