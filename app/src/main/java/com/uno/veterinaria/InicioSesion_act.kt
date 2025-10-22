package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import models.DBHelper

class InicioSesion_act : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        val dbHelper = DBHelper(this)

        // Agregar usuarios de prueba si no existen
        if (!dbHelper.checkUserExists("admin@test.com")) {
            dbHelper.agregarUsuario("Administrador", "admin@test.com", "admin123", "123456789", "admin")
        }
        if (!dbHelper.checkUserExists("user@test.com")) {
            dbHelper.agregarUsuario("Usuario de Prueba", "user@test.com", "user123", "987654321", "user")
        }

        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val tvRegistrarse = findViewById<TextView>(R.id.tvRegistrarse)

        btnIniciarSesion.setOnClickListener {
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()

            if (correo.isNotEmpty() && contrasena.isNotEmpty()) {
                val role = dbHelper.checkUser(correo, contrasena)
                if (role != null) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    if (role == "admin") {
                        val intent = Intent(this, InicioAdmin_act::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, Inicio_act::class.java)
                        startActivity(intent)
                    }
                    finish()
                } else {
                    Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegistrarse.setOnClickListener {
            val intent = Intent(this, Registro_act::class.java)
            startActivity(intent)
        }
    }
}