package com.uno.veterinaria

import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import models.DBHelper

class AgendarHora_act : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_hora)


        val etNombreMascota = findViewById<EditText>(R.id.etNombreMascota)
        val etSexoMascota = findViewById<EditText>(R.id.etSexoMascota)
        val etChipMascota = findViewById<EditText>(R.id.etChipMascota)
        val etNombreDueno = findViewById<EditText>(R.id.etNombreDueno)
        val etFecha = findViewById<EditText>(R.id.etFecha)
        val etHora = findViewById<EditText>(R.id.etHora)
        val etMotivo = findViewById<EditText>(R.id.etMotivo)
        val btnAgendar = findViewById<Button>(R.id.btnAgendar)

        // Inicializar base de datos
        dbHelper = DBHelper(this)


        btnAgendar.setOnClickListener {
            val nombreMascota = etNombreMascota.text.toString()
            val sexoMascota = etSexoMascota.text.toString()
            val chipMascota = etChipMascota.text.toString()
            val nombreDueno = etNombreDueno.text.toString()
            val fecha = etFecha.text.toString()
            val hora = etHora.text.toString()
            val motivo = etMotivo.text.toString()

            if (nombreMascota.isNotEmpty() && sexoMascota.isNotEmpty() && chipMascota.isNotEmpty() && nombreDueno.isNotEmpty() && fecha.isNotEmpty() && hora.isNotEmpty() && motivo.isNotEmpty()) {
                val db = dbHelper.writableDatabase
                val result = dbHelper.agregarCita(nombreMascota, sexoMascota, chipMascota, nombreDueno, fecha, hora, motivo)
                if (result > -1) {
                    Toast.makeText(this, "Hora agendada correctamente", Toast.LENGTH_SHORT).show()
                    etNombreMascota.text.clear()
                    etSexoMascota.text.clear()
                    etChipMascota.text.clear()
                    etNombreDueno.text.clear()
                    etFecha.text.clear()
                    etHora.text.clear()
                    etMotivo.text.clear()
                } else {
                    Toast.makeText(this, "Error al agendar la hora", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}