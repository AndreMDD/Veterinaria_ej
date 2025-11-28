package com.uno.veterinaria

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uno.veterinaria.viewmodel.FichaClinicaViewModel
import models.HistorialCita
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FichaClinica_act : AppCompatActivity() {

    private val viewModel: FichaClinicaViewModel by viewModels()
    private lateinit var historialAdapter: HistorialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_clinica)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupRecyclerView()

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", null)

        if (userName != null) {
            viewModel.cargarCitas(userName)
        }

        viewModel.citas.observe(this, Observer { citas ->
            if (citas.isNotEmpty()) {
                populateUI(citas)
            }
        })
    }

    private fun setupRecyclerView() {
        val rvHistorial: RecyclerView = findViewById(R.id.rvHistorialClinico)
        rvHistorial.layoutManager = LinearLayoutManager(this)
        historialAdapter = HistorialAdapter(emptyList())
        rvHistorial.adapter = historialAdapter
    }

    private fun populateUI(citas: List<HistorialCita>) {
        val primeraCita = citas[0]
        // CORRECCIÓN: Usamos los campos que sí existen en la API
        findViewById<TextView>(R.id.tvNombreMascotaFicha).text = "Mascota ID: ${primeraCita.mascotaId}"

        // CORRECCIÓN: Ocultamos los campos que no vienen en la API
        findViewById<TextView>(R.id.tvEspecieMascotaFicha).visibility = View.GONE
        findViewById<TextView>(R.id.tvEdadMascotaFicha).visibility = View.GONE

        // CORRECCIÓN: Ordenamos por el timestamp
        val proximaCita = citas.sortedBy { it.fechaHoraTimestamp }.lastOrNull()
        
        if(proximaCita != null) {
            // CORRECCIÓN: Formateamos el timestamp para mostrarlo
            val formattedDate = formatDate(proximaCita.fechaHoraTimestamp)
            findViewById<TextView>(R.id.tvFechaProximaCita).text = formattedDate
            findViewById<TextView>(R.id.tvMotivoProximaCita).text = "Motivo: ${proximaCita.motivo}"
        }

        historialAdapter.actualizarCitas(citas)
    }

    // Función de utilidad para formatear el timestamp
    private fun formatDate(timestamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
            val netDate = Date(timestamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            "Fecha inválida"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class HistorialAdapter(private var citas: List<HistorialCita>) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

        inner class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvFechaHistorial: TextView = itemView.findViewById(R.id.tvFechaHistorial)
            val tvMotivoHistorial: TextView = itemView.findViewById(R.id.tvMotivoHistorial)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_historial, parent, false)
            return HistorialViewHolder(view)
        }

        override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
            val cita = citas[position]
            // CORRECCIÓN: Formateamos el timestamp para mostrar la fecha y hora
            holder.tvFechaHistorial.text = formatDate(cita.fechaHoraTimestamp)
            holder.tvMotivoHistorial.text = "Motivo: ${cita.motivo}"
        }

        override fun getItemCount(): Int = citas.size

        fun actualizarCitas(nuevasCitas: List<HistorialCita>) {
            citas = nuevasCitas
            notifyDataSetChanged()
        }
    }
}
