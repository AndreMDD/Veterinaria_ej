package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import models.DBHelper

class AgendarHora_act : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_hora)

        // Inicializar la base de datos
        dbHelper = DBHelper(this)

        // Referencias a los nuevos componentes de Material Design
        val etNombreMascota = findViewById<TextInputEditText>(R.id.etNombreMascota)
        val etRazaMascota = findViewById<TextInputEditText>(R.id.etRazaMascota)
        val etEdadMascota = findViewById<TextInputEditText>(R.id.etEdadMascota)
        val etSexoMascota = findViewById<TextInputEditText>(R.id.etSexoMascota)
        val etChipMascota = findViewById<TextInputEditText>(R.id.etChipMascota)
        val etNombreDueno = findViewById<TextInputEditText>(R.id.etNombreDueno)
        val etFecha = findViewById<TextInputEditText>(R.id.etFecha)
        val etHora = findViewById<TextInputEditText>(R.id.etHora)
        val etMotivo = findViewById<TextInputEditText>(R.id.etMotivo)
        val btnAgendar = findViewById<Button>(R.id.btnAgendar)

        // Lógica para agendar la cita
        btnAgendar.setOnClickListener {
            val nombreMascota = etNombreMascota.text.toString()
            val raza = etRazaMascota.text.toString()
            val edad = etEdadMascota.text.toString()
            val sexoMascota = etSexoMascota.text.toString()
            val chipMascota = etChipMascota.text.toString()
            val nombreDueno = etNombreDueno.text.toString()
            val fecha = etFecha.text.toString()
            val hora = etHora.text.toString()
            val motivo = etMotivo.text.toString()

            if (nombreMascota.isNotEmpty() && raza.isNotEmpty() && edad.isNotEmpty() && sexoMascota.isNotEmpty() && nombreDueno.isNotEmpty() && fecha.isNotEmpty() && hora.isNotEmpty() && motivo.isNotEmpty()) {
                val result = dbHelper.agregarCita(nombreMascota, sexoMascota, chipMascota, nombreDueno, fecha, hora, motivo, raza, edad)
                if (result > -1) {
                    Toast.makeText(this, "Hora agendada correctamente", Toast.LENGTH_SHORT).show()
                    // Navegamos a Inicio y limpiamos el historial
                    val intent = Intent(this, Inicio_act::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error al agendar la hora", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Esta función es llamada por el atributo android:onClick del botón 'btnVolver'
     * en el archivo activity_agendar_hora.xml
     */
    fun volver(view: View) {
        val intent = Intent(this, Inicio_act::class.java)
        // Limpiamos la pila de actividades para una navegación más limpia
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}