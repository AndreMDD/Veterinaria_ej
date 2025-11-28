package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import models.DBHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AgendarHora_act : AppCompatActivity() {

    private lateinit var tilNombreMascota: TextInputLayout
    private lateinit var tilEspecieMascota: TextInputLayout
    private lateinit var tilEdadMascota: TextInputLayout
    private lateinit var tilSexoMascota: TextInputLayout
    private lateinit var tilChipMascota: TextInputLayout
    private lateinit var tilNombreDueno: TextInputLayout
    private lateinit var tilFecha: TextInputLayout
    private lateinit var tilHora: TextInputLayout
    private lateinit var tilMotivo: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_hora)

        // Initialize TextInputLayouts
        tilNombreMascota = findViewById(R.id.tilNombreMascota)
        tilEspecieMascota = findViewById(R.id.tilEspecieMascota)
        tilEdadMascota = findViewById(R.id.tilEdadMascota)
        tilSexoMascota = findViewById(R.id.tilSexoMascota)
        tilChipMascota = findViewById(R.id.tilChipMascota)
        tilNombreDueno = findViewById(R.id.tilNombreDueno)
        tilFecha = findViewById(R.id.tilFecha)
        tilHora = findViewById(R.id.tilHora)
        tilMotivo = findViewById(R.id.tilMotivo)

        // Set click listeners for date and time fields
        tilFecha.editText?.setOnClickListener {
            showDatePickerDialog()
        }

        tilHora.editText?.setOnClickListener {
            showTimePickerDialog()
        }

        findViewById<View>(R.id.btnAgendar).setOnClickListener {
            if (validateFields()) {
                val dbHelper = DBHelper(this)
                val nombreMascota = tilNombreMascota.editText?.text.toString()
                val especieMascota = tilEspecieMascota.editText?.text.toString()
                val edadMascota = tilEdadMascota.editText?.text.toString()
                val sexoMascota = tilSexoMascota.editText?.text.toString()
                val chipMascota = tilChipMascota.editText?.text.toString()
                val nombreDueno = tilNombreDueno.editText?.text.toString()
                val fecha = tilFecha.editText?.text.toString()
                val hora = tilHora.editText?.text.toString()
                val motivo = tilMotivo.editText?.text.toString()

                val result = dbHelper.agregarCita(
                    nombreMascota,
                    sexoMascota,
                    chipMascota,
                    nombreDueno,
                    fecha,
                    hora,
                    motivo,
                    especieMascota,
                    edadMascota
                )

                if (result != -1L) {
                    Toast.makeText(this, "Cita agendada exitosamente", Toast.LENGTH_SHORT).show()

                    // Borrar datos de las casillas
                    tilNombreMascota.editText?.text?.clear()
                    tilEspecieMascota.editText?.text?.clear()
                    tilEdadMascota.editText?.text?.clear()
                    tilSexoMascota.editText?.text?.clear()
                    tilChipMascota.editText?.text?.clear()
                    tilNombreDueno.editText?.text?.clear()
                    tilFecha.editText?.text?.clear()
                    tilHora.editText?.text?.clear()
                    tilMotivo.editText?.text?.clear()

                    // Redirigir a Inicio_act
                    val intent = Intent(this, Inicio_act::class.java)
                    startActivity(intent)
                    finish() // Cierra la actividad actual
                } else {
                    Toast.makeText(this, "Error al agendar la cita", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecciona una fecha")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val date = sdf.format(Date(selection))
            tilFecha.editText?.setText(date)
        }

        datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
    }

    private fun showTimePickerDialog() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Selecciona una hora")
            .build()

        picker.addOnPositiveButtonClickListener { 
            val selectedTime = String.format("%02d:%02d", picker.hour, picker.minute)
            tilHora.editText?.setText(selectedTime)
        }

        picker.show(supportFragmentManager, "MATERIAL_TIME_PICKER")
    }

    private fun validateFields(): Boolean {
        // Reset errors
        tilNombreMascota.error = null
        tilEspecieMascota.error = null
        tilEdadMascota.error = null
        tilSexoMascota.error = null
        tilChipMascota.error = null
        tilNombreDueno.error = null
        tilFecha.error = null
        tilHora.error = null
        tilMotivo.error = null

        var isValid = true
        if (tilNombreMascota.editText?.text.toString().trim().isEmpty()) {
            tilNombreMascota.error = "El campo debe estar completo"
            isValid = false
        }
        if (tilEspecieMascota.editText?.text.toString().trim().isEmpty()) {
            tilEspecieMascota.error = "El campo debe estar completo"
            isValid = false
        }
        if (tilEdadMascota.editText?.text.toString().trim().isEmpty()) {
            tilEdadMascota.error = "El campo debe estar completo"
            isValid = false
        }
        if (tilSexoMascota.editText?.text.toString().trim().isEmpty()) {
            tilSexoMascota.error = "El campo debe estar completo"
            isValid = false
        }
        if (tilChipMascota.editText?.text.toString().trim().isEmpty()) {
            // Opcional, no se valida
        }
        if (tilNombreDueno.editText?.text.toString().trim().isEmpty()) {
            tilNombreDueno.error = "El campo debe estar completo"
            isValid = false
        }
        if (tilFecha.editText?.text.toString().trim().isEmpty()) {
            tilFecha.error = "Por favor, selecciona una fecha"
            isValid = false
        }
        if (tilHora.editText?.text.toString().trim().isEmpty()) {
            tilHora.error = "Por favor, selecciona una hora"
            isValid = false
        }
        if (tilMotivo.editText?.text.toString().trim().isEmpty()) {
            tilMotivo.error = "El campo debe estar completo"
            isValid = false
        }

        return isValid
    }

    fun volver(view: View) {
        finish()
    }
}
