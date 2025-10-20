package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Registro_act : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val etNombreCompleto = findViewById<EditText>(R.id.etNombreCompleto)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val etConfirmarContrasena = findViewById<EditText>(R.id.etConfirmarContrasena)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val tvIniciarSesion = findViewById<TextView>(R.id.tvIniciarSesion)

        btnRegistrarse.setOnClickListener {
            val nombreCompleto = etNombreCompleto.text.toString()
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()
            val confirmarContrasena = etConfirmarContrasena.text.toString()
            val hola = "Hola"
            val sad = "Hola"

            if (nombreCompleto.isNotEmpty() && correo.isNotEmpty() && contrasena.isNotEmpty() && confirmarContrasena.isNotEmpty()) {
                if (contrasena == confirmarContrasena) {
                    // Lógica de registro (puedes agregar aquí la integración con Firebase o una base de datos local)
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, InicioSesion_act::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        tvIniciarSesion.setOnClickListener {
            val intent = Intent(this, InicioSesion_act::class.java)
            startActivity(intent)
        }
    }
}
