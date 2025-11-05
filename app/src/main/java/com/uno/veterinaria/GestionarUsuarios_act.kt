package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import models.DBHelper

class GestionarUsuarios_act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_usuarios)

        val dbHelper = DBHelper(this)

        val etNombreCompleto = findViewById<TextInputEditText>(R.id.etNombreCompleto)
        val etCorreo = findViewById<TextInputEditText>(R.id.etCorreo)
        val etTelefono = findViewById<TextInputEditText>(R.id.etTelefono)
        val etContrasena = findViewById<TextInputEditText>(R.id.etContrasena)
        val btnRegistrarAdmin = findViewById<Button>(R.id.btnRegistrarAdmin)

        btnRegistrarAdmin.setOnClickListener {
            val nombreCompleto = etNombreCompleto.text.toString()
            val correo = etCorreo.text.toString()
            val telefono = etTelefono.text.toString()
            val contrasena = etContrasena.text.toString()

            if (nombreCompleto.isNotEmpty() && correo.isNotEmpty() && telefono.isNotEmpty() && contrasena.isNotEmpty()) {
                if (!dbHelper.checkUserExists(correo)) {
                    val result = dbHelper.agregarUsuario(nombreCompleto, correo, contrasena, telefono, "admin")
                    if (result > -1) {
                        Toast.makeText(this, "Administrador registrado exitosamente", Toast.LENGTH_SHORT).show()
                        etNombreCompleto.text?.clear()
                        etCorreo.text?.clear()
                        etTelefono.text?.clear()
                        etContrasena.text?.clear()
                    } else {
                        Toast.makeText(this, "Error al registrar el administrador", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun volver(view: View) {
        val intent = Intent(this, InicioAdmin_act::class.java)
        // Limpiamos la pila de actividades para una navegación más limpia
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}