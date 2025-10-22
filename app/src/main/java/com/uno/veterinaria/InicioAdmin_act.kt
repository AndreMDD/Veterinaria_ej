package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InicioAdmin_act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_admin)

        val btnVerCitas = findViewById<Button>(R.id.btnVerCitas)
        val btnGestionarUsuarios = findViewById<Button>(R.id.btnGestionarUsuarios)
        val btnVerHistorialMascotas = findViewById<Button>(R.id.btnVerHistorialMascotas)

        btnVerCitas.setOnClickListener {
            val intent = Intent(this, CitasAgendadasAdmin_act::class.java)
            startActivity(intent)
        }

        btnGestionarUsuarios.setOnClickListener {
            // Lógica para gestionar usuarios
            Toast.makeText(this, "Función para gestionar usuarios próximamente", Toast.LENGTH_SHORT).show()
        }

        btnVerHistorialMascotas.setOnClickListener {
            // Lógica para ver historial de mascotas
            Toast.makeText(this, "Función para ver historial de mascotas próximamente", Toast.LENGTH_SHORT).show()
        }
    }
}