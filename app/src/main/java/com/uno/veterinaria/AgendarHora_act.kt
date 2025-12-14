package com.uno.veterinaria

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.uno.veterinaria.viewmodel.AgendarHoraViewModel
import models.AgendarCitaRequest
import models.HistorialCita
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AgendarHora_act : AppCompatActivity() {

    private val viewModel: AgendarHoraViewModel by viewModels()

    private lateinit var tilNombreMascota: TextInputLayout
    private lateinit var tilEspecieMascota: TextInputLayout
    private lateinit var tilEdadMascota: TextInputLayout
    private lateinit var tilSexoMascota: TextInputLayout
    private lateinit var tilChipMascota: TextInputLayout
    private lateinit var tilNombreDueno: TextInputLayout
    private lateinit var tilFecha: TextInputLayout
    private lateinit var tilHora: TextInputLayout
    private lateinit var tilMotivo: TextInputLayout

    private var selectedDateTimestamp: Long = 0L

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(this, "No se podrán recibir notificaciones de citas.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_hora)

        initializeViews()
        setupClickListeners()
        observeViewModel()
        requestNotificationPermission()
    }

    private fun initializeViews() {
        tilNombreMascota = findViewById(R.id.tilNombreMascota)
        tilEspecieMascota = findViewById(R.id.tilEspecieMascota)
        tilEdadMascota = findViewById(R.id.tilEdadMascota)
        tilSexoMascota = findViewById(R.id.tilSexoMascota)
        tilChipMascota = findViewById(R.id.tilChipMascota)
        tilNombreDueno = findViewById(R.id.tilNombreDueno)
        tilFecha = findViewById(R.id.tilFecha)
        tilHora = findViewById(R.id.tilHora)
        tilMotivo = findViewById(R.id.tilMotivo)
    }

    private fun setupClickListeners() {
        tilFecha.editText?.setOnClickListener { showDatePickerDialog() }
        tilHora.editText?.setOnClickListener { showTimePickerDialog() }

        findViewById<View>(R.id.btnAgendar).setOnClickListener {
            if (validateFields()) {
                agendarCitaViaApi()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.agendamientoResult.observe(this, Observer { result ->
            result.onSuccess { citaCreada ->
                Toast.makeText(this, "Cita agendada exitosamente", Toast.LENGTH_LONG).show()
                showCitaNotification(citaCreada)
                shareCitaDetails(citaCreada)
                clearForm()
            }
            result.onFailure { error ->
                Toast.makeText(this, "Error al agendar: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @SuppressLint("MissingPermission") // Se añade para suprimir el error de Lint, ya que la comprobación se hace manualmente.
    private fun showCitaNotification(cita: HistorialCita) {
        val notificationId = cita.id
        val builder = NotificationCompat.Builder(this, VeterinariaApplication.CHANNEL_ID_CITA)
            .setSmallIcon(R.drawable.ic_pets) // Asegúrate de tener este ícono
            .setContentTitle("Cita Agendada Exitosamente")
            .setContentText("Tu cita para el ${formatDate(cita.fechaHoraTimestamp, "dd/MM/yyyy")} ha sido confirmada.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(this).notify(notificationId, builder.build())
        }
    }

    private fun shareCitaDetails(cita: HistorialCita) {
        var shareText = "¡Hola! Te recuerdo nuestra cita en la veterinaria:\n"
        shareText += "Mascota ID: ${cita.mascotaId}\n"
        shareText += "Fecha y Hora: ${formatDate(cita.fechaHoraTimestamp, "dd/MM/yyyy 'a las' HH:mm")}\n"
        shareText += "Motivo: ${cita.motivo}"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Compartir detalles de la cita")
        startActivity(shareIntent)
    }

    private fun agendarCitaViaApi() {
        val finalTimestamp = combineDateAndTime(selectedDateTimestamp, tilHora.editText?.text.toString())
        val request = AgendarCitaRequest(
            nombreMascota = tilNombreMascota.editText?.text.toString(),
            especie = tilEspecieMascota.editText?.text.toString(),
            edad = tilEdadMascota.editText?.text.toString().toIntOrNull() ?: 0,
            sexo = tilSexoMascota.editText?.text.toString(),
            chip = tilChipMascota.editText?.text.toString().ifEmpty { null },
            nombreDueno = tilNombreDueno.editText?.text.toString(),
            fechaHoraTimestamp = finalTimestamp,
            motivo = tilMotivo.editText?.text.toString()
        )
        viewModel.agendarNuevaCita(request)
    }

    private fun showDatePickerDialog() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Selecciona una fecha").build()
        datePicker.addOnPositiveButtonClickListener { selection ->
            this.selectedDateTimestamp = selection
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            tilFecha.editText?.setText(sdf.format(Date(selection)))
        }
        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }

    private fun showTimePickerDialog() {
        val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
        picker.addOnPositiveButtonClickListener { 
            tilHora.editText?.setText(String.format("%02d:%02d", picker.hour, picker.minute))
        }
        picker.show(supportFragmentManager, "TIME_PICKER")
    }

    private fun combineDateAndTime(dateTimestamp: Long, timeStr: String): Long {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = dateTimestamp
        val timeParts = timeStr.split(":")
        if (timeParts.size == 2) {
            calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
            calendar.set(Calendar.MINUTE, timeParts[1].toInt())
        }
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun clearForm() {
        tilNombreMascota.editText?.text?.clear()
        tilEspecieMascota.editText?.text?.clear()
        tilEdadMascota.editText?.text?.clear()
        tilSexoMascota.editText?.text?.clear()
        tilChipMascota.editText?.text?.clear()
        tilNombreDueno.editText?.text?.clear()
        tilFecha.editText?.text?.clear()
        tilHora.editText?.text?.clear()
        tilMotivo.editText?.text?.clear()
    }

    private fun validateFields(): Boolean {
        var isValid = true
        if (tilNombreMascota.editText?.text.toString().trim().isEmpty()) {
            tilNombreMascota.error = "El campo debe estar completo"
            isValid = false
        } else tilNombreMascota.error = null

        if (tilEspecieMascota.editText?.text.toString().trim().isEmpty()) {
            tilEspecieMascota.error = "El campo debe estar completo"
            isValid = false
        } else tilEspecieMascota.error = null

        if (tilEdadMascota.editText?.text.toString().trim().isEmpty()) {
            tilEdadMascota.error = "El campo debe estar completo"
            isValid = false
        } else tilEdadMascota.error = null

        if (tilSexoMascota.editText?.text.toString().trim().isEmpty()) {
            tilSexoMascota.error = "El campo debe estar completo"
            isValid = false
        } else tilSexoMascota.error = null

        if (tilNombreDueno.editText?.text.toString().trim().isEmpty()) {
            tilNombreDueno.error = "El campo debe estar completo"
            isValid = false
        } else tilNombreDueno.error = null

        if (tilFecha.editText?.text.toString().trim().isEmpty()) {
            tilFecha.error = "El campo debe estar completo"
            isValid = false
        } else tilFecha.error = null

        if (tilHora.editText?.text.toString().trim().isEmpty()) {
            tilHora.error = "El campo debe estar completo"
            isValid = false
        } else tilHora.error = null

        if (tilMotivo.editText?.text.toString().trim().isEmpty()) {
            tilMotivo.error = "El campo debe estar completo"
            isValid = false
        } else tilMotivo.error = null

        return isValid
    }
    
    private fun formatDate(timestamp: Long, pattern: String): String {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val netDate = Date(timestamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            "Fecha inválida"
        }
    }

    fun volver(view: View) {
        finish()
    }
}
