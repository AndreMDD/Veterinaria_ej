package com.uno.veterinaria

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class FichaClinica_act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_clinica)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Recibir datos del Intent
        val nombreMascota = intent.getStringExtra("nombreMascota")
        val especieMascota = intent.getStringExtra("especieMascota")
        val edadMascota = intent.getStringExtra("edadMascota")
        val fechaCita = intent.getStringExtra("fechaCita")
        val motivoCita = intent.getStringExtra("motivoCita")

        // Encontrar los TextViews en el layout
        val tvNombreMascota: TextView = findViewById(R.id.tvNombreMascotaFicha)
        val tvEspecieMascota: TextView = findViewById(R.id.tvEspecieMascotaFicha)
        val tvEdadMascota: TextView = findViewById(R.id.tvEdadMascotaFicha)
        val tvFechaProximaCita: TextView = findViewById(R.id.tvFechaProximaCita)
        val tvMotivoProximaCita: TextView = findViewById(R.id.tvMotivoProximaCita)

        // Asignar los datos a los TextViews
        tvNombreMascota.text = nombreMascota
        tvEspecieMascota.text = especieMascota
        tvEdadMascota.text = edadMascota
        tvFechaProximaCita.text = fechaCita
        tvMotivoProximaCita.text = "Motivo: $motivoCita"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}