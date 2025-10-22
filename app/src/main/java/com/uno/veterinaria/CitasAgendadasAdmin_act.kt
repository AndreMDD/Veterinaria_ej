package com.uno.veterinaria

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.DBHelper

class CitasAgendadasAdmin_act : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas_agendadas_admin)

        val dbHelper = DBHelper(this)
        val rvCitas = findViewById<RecyclerView>(R.id.rvCitas)
        rvCitas.layoutManager = LinearLayoutManager(this)

        val citas = dbHelper.getAllCitas()
        val adapter = CitaAdapter(citas)
        rvCitas.adapter = adapter
    }
}