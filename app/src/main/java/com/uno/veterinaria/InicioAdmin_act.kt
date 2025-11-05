package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class InicioAdmin_act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_admin)

        val btnVerCitas = findViewById<MaterialCardView>(R.id.btnVerCitas)
        val btnGestionarUsuarios = findViewById<MaterialCardView>(R.id.btnGestionarUsuarios)
        val btnVerHistorialMascotas = findViewById<MaterialCardView>(R.id.btnVerHistorialMascotas)
        val btnCerrarSesion = findViewById<MaterialCardView>(R.id.btnCerrarSesion)

        btnVerCitas.setOnClickListener {
            val intent = Intent(this, CitasAgendadas_act::class.java)
            startActivity(intent)
        }

        btnGestionarUsuarios.setOnClickListener {
            val intent = Intent(this, GestionarUsuarios_act::class.java)
            startActivity(intent)
        }

        btnVerHistorialMascotas.setOnClickListener {
            Toast.makeText(this, "Función para ver historial de mascotas próximamente", Toast.LENGTH_SHORT).show()
        }

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}