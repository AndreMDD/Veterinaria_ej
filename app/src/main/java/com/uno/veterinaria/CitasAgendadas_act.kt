package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import models.DBHelper

class CitasAgendadas_act : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas_agendadas)

        val dbHelper = DBHelper(this)
        val rvCitas = findViewById<RecyclerView>(R.id.rvCitas)

        // 1. Obtenemos la lista de citas de la base de datos
        val citas = dbHelper.getAllCitas()

        // 2. Creamos una instancia de nuestro nuevo CitaAdapter
        val adapter = CitaAdapter(citas)

        // 3. Asignamos el adaptador al RecyclerView
        rvCitas.adapter = adapter
    }

    fun volver(view: View) {
        val intent = Intent(this, InicioAdmin_act::class.java)
        // Limpiamos la pila de actividades para una navegación más limpia
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}