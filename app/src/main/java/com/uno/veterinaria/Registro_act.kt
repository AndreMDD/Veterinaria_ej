package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import models.DBHelper

class Registro_act : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val dbHelper = DBHelper(this)

        val etNombreCompleto = findViewById<EditText>(R.id.etNombreCompleto)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val etConfirmarContrasena = findViewById<EditText>(R.id.etConfirmarContrasena)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val tvIniciarSesion = findViewById<TextView>(R.id.tvIniciarSesion)

        btnRegistrarse.setOnClickListener {
            val nombreCompleto = etNombreCompleto.text.toString()
            val correo = etCorreo.text.toString()
            val telefono = etTelefono.text.toString()
            val contrasena = etContrasena.text.toString()
            val confirmarContrasena = etConfirmarContrasena.text.toString()

            if (nombreCompleto.isNotEmpty() && correo.isNotEmpty() && telefono.isNotEmpty() && contrasena.isNotEmpty() && confirmarContrasena.isNotEmpty()) {
                if (contrasena == confirmarContrasena) {
                    if (!dbHelper.checkUserExists(correo)) {
                        val result = dbHelper.agregarUsuario(nombreCompleto, correo, contrasena, telefono, "user")
                        if (result > -1) {
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, InicioSesion_act::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                    }
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