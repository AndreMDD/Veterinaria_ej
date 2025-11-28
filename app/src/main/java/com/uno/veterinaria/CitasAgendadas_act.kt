package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uno.veterinaria.adapter.CitaAdapter
import com.uno.veterinaria.viewmodel.CitasAgendadasViewModel

class CitasAgendadas_act : AppCompatActivity() {

    private val viewModel: CitasAgendadasViewModel by viewModels()
    private lateinit var citaAdapter: CitaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas_agendadas)

        setupRecyclerView()

        // Observamos el LiveData que contiene la lista de citas
        viewModel.citas.observe(this, Observer { citas ->
            // Cuando los datos cambian, actualizamos el adaptador
            citaAdapter.actualizarCitas(citas)
        })

        // Observamos el LiveData de errores para mostrar un mensaje al usuario
        viewModel.error.observe(this, Observer { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        })

        // Le pedimos al ViewModel que inicie la carga de datos
        viewModel.cargarCitasAgendadas()
    }

    private fun setupRecyclerView() {
        val rvCitas = findViewById<RecyclerView>(R.id.rvCitas)
        rvCitas.layoutManager = LinearLayoutManager(this)
        // Creamos el adaptador con una lista inicialmente vacía
        citaAdapter = CitaAdapter(emptyList())
        rvCitas.adapter = citaAdapter
    }

    fun volver(view: View) {
        val intent = Intent(this, InicioAdmin_act::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
