package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.uno.veterinaria.repository.CitasRepository
import com.uno.veterinaria.repository.WeatherRepository
import com.uno.veterinaria.viewmodel.InicioViewModel
import com.uno.veterinaria.viewmodel.InicioViewModelFactory
import de.hdodenhof.circleimageview.CircleImageView

class Inicio_act : AppCompatActivity() {

    // CORRECCIÓN: Se inicializa el ViewModel usando la fábrica para inyectar las dependencias.
    private val viewModel: InicioViewModel by lazy {
        val citasRepository = CitasRepository()
        val weatherRepository = WeatherRepository()
        val factory = InicioViewModelFactory(citasRepository, weatherRepository)
        ViewModelProvider(this, factory)[InicioViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val horasBtn = findViewById<Button>(R.id.horas_btn)
        val appointmentButton = findViewById<Button>(R.id.appointmentButton)
        val fichaClinicaBtn = findViewById<Button>(R.id.fichaClinica_btn)
        val profileImage = findViewById<CircleImageView>(R.id.profile_image)
        val locationButton = findViewById<Button>(R.id.locationButton)

        horasBtn.setOnClickListener {
            val intent = Intent(this, AgendarHora_act::class.java)
            startActivity(intent)
        }

        appointmentButton.setOnClickListener {
            val intent = Intent(this, AgendarHora_act::class.java)
            startActivity(intent)
        }

        fichaClinicaBtn.setOnClickListener {
            val intent = Intent(this, FichaClinica_act::class.java)
            startActivity(intent)
        }

        locationButton.setOnClickListener {
            viewModel.cargarUbicacion()
        }

        viewModel.ubicacion.observe(this, Observer { ubicacion ->
            Toast.makeText(this, "Nuestra dirección es: ${ubicacion.direccion}", Toast.LENGTH_LONG).show()
        })

        profileImage.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.overflow_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_profile -> {
                        val intent = Intent(this, Profile_act::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.action_logout -> {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
}
