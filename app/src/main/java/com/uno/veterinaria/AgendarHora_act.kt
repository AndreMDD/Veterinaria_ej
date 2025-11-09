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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AgendarHora_act : AppCompatActivity() {

    private lateinit var tilNombreMascota: TextInputLayout
    private lateinit var tilEspecieMascota: TextInputLayout
    private lateinit var tilEdadMascota: TextInputLayout
    private lateinit var tilSexoMascota: TextInputLayout
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
                // Lógica para guardar la cita
                Toast.makeText(this, "Cita agendada exitosamente", Toast.LENGTH_SHORT).show()

                // Borrar datos de las casillas
                tilNombreMascota.editText?.text?.clear()
                tilEspecieMascota.editText?.text?.clear()
                tilEdadMascota.editText?.text?.clear()
                tilSexoMascota.editText?.text?.clear()
                tilNombreDueno.editText?.text?.clear()
                tilFecha.editText?.text?.clear()
                tilHora.editText?.text?.clear()
                tilMotivo.editText?.text?.clear()

                // Redirigir a Inicio_act
                val intent = Intent(this, Inicio_act::class.java)
                startActivity(intent)
                finish() // Cierra la actividad actual
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecciona una fecha")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            // The selection is in UTC milliseconds. Convert it to a readable date string.
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC") // Important: Use UTC to format the selection
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
        tilNombreDueno.error = null
        tilFecha.error = null
        tilHora.error = null
        tilMotivo.error = null

        // Validation logic
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